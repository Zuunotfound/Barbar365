package com.example.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

sealed class Screen {
    object Welcome : Screen()
    object Login : Screen()
    object Register : Screen()
    object PendingApproval : Screen()
    object Rejected : Screen()
    object Suspended : Screen()
    
    // Admin Views
    object AdminDashboard : Screen()
    object AdminEmployeesList : Screen()
    object AdminCreateTask : Screen()
    object AdminReviewSubmissions : Screen()
    object AdminReviewBonusClaims : Screen()
    object AdminReviewPayments : Screen()
    object AdminSupportTickets : Screen()
    object AdminNDPTracking : Screen()
    object AdminConfigSettings : Screen()
    object AdminContentCalendar : Screen()
    
    // Owner Views
    object OwnerDashboard : Screen()
    object OwnerAdminManagement : Screen()
    object OwnerFinancialOverview : Screen()
    
    // Employee Views
    object EmployeeDashboard : Screen()
    object Ms001Registration: Screen()
    object EmployeeTasksList : Screen()
    object EmployeeSubmitProof : Screen()
    object EmployeeClaimBonusFYP : Screen()
    object EmployeePaymentHistory : Screen()
    object EmployeeAnnouncements : Screen()
    object EmployeeSupportTickets : Screen()
    object EmployeeProfile : Screen()

    // Service Engineer Views
    object ServiceEngineerDashboard : Screen()
}

class ZuuViewModel(val repository: ZuuRepository, val context: Context) : ViewModel() {

    val supabaseClient = SupabaseClient(context)
    private val appPrefs = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)

    // --- Configurations (Admin Custom Settings) ---
    var minCompletionRate = MutableStateFlow(appPrefs.getInt("min_completion_rate", 80)) // default 80%
    var maxRejectionCount = MutableStateFlow(appPrefs.getInt("max_rejection_count", 2)) // default max 2 rejections

    fun saveConfigSettings(rate: Int, maxRej: Int) {
        minCompletionRate.value = rate
        maxRejectionCount.value = maxRej
        appPrefs.edit()
            .putInt("min_completion_rate", rate)
            .putInt("max_rejection_count", maxRej)
            .apply()
    }

    // --- Sync States ---
    val isSyncing = MutableStateFlow(false)
    val syncErrorMessage = MutableStateFlow<String?>(null)

    // --- Session States ---
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _currentScreen = MutableStateFlow<Screen>(Screen.Welcome)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    // --- Registration Status Display helper ---
    val registrationSuccessMessage = MutableStateFlow<String?>(null)
    val registeredEmployeeId = MutableStateFlow<String?>(null)

    // --- Flows from Repository ---
    val users: StateFlow<List<User>> = repository.allUsers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val tasks: StateFlow<List<Task>> = repository.allTasks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val submissions: StateFlow<List<TaskSubmission>> = repository.allSubmissions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val checkins: StateFlow<List<DailyCheckIn>> = repository.allCheckIns
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val warnings: StateFlow<List<Warning>> = repository.allWarnings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val payments: StateFlow<List<Payment>> = repository.allPayments
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val announcements: StateFlow<List<Announcement>> = repository.allAnnouncements
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val tickets: StateFlow<List<SupportTicket>> = repository.allTickets
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val logs: StateFlow<List<ActivityLog>> = repository.allLogs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bonusClaims: StateFlow<List<BonusClaim>> = repository.allBonusClaims
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val ndpRecords: StateFlow<List<WeeklyNdp>> = repository.allNdp
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val salaries: StateFlow<List<SalaryRecord>> = repository.allSalaries
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val withdrawalRequests: StateFlow<List<WithdrawalRequest>> = repository.allWithdrawalRequests
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val isCleared = appPrefs.getBoolean("db_cleared_for_user_supabase_v4", false)
            if (!isCleared) {
                repository.clearAllData()
                appPrefs.edit()
                    .putBoolean("db_cleared_for_user_supabase_v4", true)
                    .remove("logged_in_wa")
                    .remove("logged_in_pw")
                    .apply()
                _currentUser.value = null
                _currentScreen.value = Screen.Welcome
                Log.d("ZuuViewModel", "Cleared all old local data 100% for the fresh setup.")
            } else {
                // Sync users with Supabase
                if (supabaseClient.isConfigured) {
                    try {
                        val remoteU = supabaseClient.getRemoteUsers()
                        remoteU.forEach { repository.insertUser(it) }
                    } catch (e: Exception) {
                        Log.e("ZuuViewModel", "Init sync error", e)
                    }
                }
            }

            // Seed base Owner, Admin, and Service Engineer accounts if they do not exist
            try {
                // 1. Owner: Doyok
                val existingOwner = repository.getUserByWhatsApp("08123456789")
                if (existingOwner == null) {
                    val seedOwner = User(
                        fullName = "Doyok",
                        whatsappNumber = "08123456789",
                        passwordHash = "doyok",
                        role = "Owner",
                        employeeId = "OWNER-001",
                        status = "Active"
                    )
                    repository.insertUser(seedOwner)
                    if (supabaseClient.isConfigured) {
                        try { supabaseClient.upsertRemoteUser(seedOwner) } catch (e: Exception) {}
                    }
                } else if (existingOwner.fullName == "Owner Barbar" || existingOwner.fullName == "Owner Zuu") {
                    val updatedOwner = existingOwner.copy(fullName = "Doyok", passwordHash = "doyok")
                    repository.insertUser(updatedOwner)
                    if (supabaseClient.isConfigured) {
                        try { supabaseClient.upsertRemoteUser(updatedOwner) } catch (e: Exception) {}
                    }
                }

                // 2. Admins (Dill, Aura, David, Hiya)
                val adminsToSeed = listOf(
                    Triple("Dill", "08111111111", "dill"),
                    Triple("Aura", "08111111112", "aura"),
                    Triple("David", "08111111113", "david"),
                    Triple("Hiya", "08111111114", "hiya")
                )
                adminsToSeed.forEachIndexed { index, adm ->
                    val existingAdmin = repository.getUserByWhatsApp(adm.second)
                    if (existingAdmin == null) {
                        val idNum = String.format("%03d", index + 1)
                        val seedAdmin = User(
                            fullName = adm.first,
                            whatsappNumber = adm.second,
                            passwordHash = adm.third,
                             role = "Admin",
                            employeeId = "ADM-$idNum",
                            status = "Active"
                        )
                        repository.insertUser(seedAdmin)
                        if (supabaseClient.isConfigured) {
                            try { supabaseClient.upsertRemoteUser(seedAdmin) } catch (e: Exception) {}
                        }
                    }
                }

                // 3. Service Engineer: Zena
                val existingEngineer = repository.getUserByWhatsApp("08555555555")
                if (existingEngineer == null) {
                    val seedEng = User(
                        fullName = "Zena",
                        whatsappNumber = "08555555555",
                        passwordHash = "zena",
                        role = "Service Engineer",
                        employeeId = "ENG-001",
                        status = "Active"
                    )
                    repository.insertUser(seedEng)
                    if (supabaseClient.isConfigured) {
                        try { supabaseClient.upsertRemoteUser(seedEng) } catch (e: Exception) {}
                    }
                } else if (existingEngineer.fullName == "Service Engineer") {
                    val updatedEng = existingEngineer.copy(fullName = "Zena", passwordHash = "zena")
                    repository.insertUser(updatedEng)
                    if (supabaseClient.isConfigured) {
                        try { supabaseClient.upsertRemoteUser(updatedEng) } catch (e: Exception) {}
                    }
                }
            } catch (e: Exception) {
                Log.e("BarbarViewModel", "Error seeding default accounts", e)
            }

            // RESTORE SESSION
            val savedWa = appPrefs.getString("logged_in_wa", null)
            val savedPw = appPrefs.getString("logged_in_pw", null)
            if (!savedWa.isNullOrBlank() && !savedPw.isNullOrBlank()) {
                var user = if (supabaseClient.isConfigured) {
                    try {
                        supabaseClient.getRemoteUserByWhatsApp(savedWa)?.also {
                            repository.insertUser(it)
                        }
                    } catch (e: Exception) {
                        null
                    }
                } else {
                    null
                }
                if (user == null) {
                    user = repository.getUserByWhatsApp(savedWa)
                }
                if (user != null && user.passwordHash == savedPw) {
                    _currentUser.value = user
                    logActivity(user.employeeId, "AutoLogin", "Sesi masuk otomatis dipulihkan.")
                    withContext(Dispatchers.Main) {
                        when (user.status) {
                            "Pending" -> _currentScreen.value = Screen.PendingApproval
                            "Rejected" -> _currentScreen.value = Screen.Rejected
                            "Suspended" -> _currentScreen.value = Screen.Suspended
                            "Active" -> {
                                when (user.role) {
                                    "Owner" -> _currentScreen.value = Screen.OwnerDashboard
                                    "Admin" -> _currentScreen.value = Screen.AdminDashboard
                                    "Service Engineer" -> _currentScreen.value = Screen.ServiceEngineerDashboard
                                    else -> {
                                        if (user.ms001Username.isBlank() || user.ms001ReferralLink.isBlank()) {
                                            _currentScreen.value = Screen.Ms001Registration
                                        } else {
                                            _currentScreen.value = Screen.EmployeeDashboard
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun triggerBackgroundSync() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (supabaseClient.isConfigured) {
                    val remoteU = supabaseClient.getRemoteUsers()
                    remoteU.forEach { repository.insertUser(it) }

                    val remoteT = supabaseClient.getRemoteTasks()
                    remoteT.forEach { repository.insertTask(it) }

                    val remoteAnn = supabaseClient.getRemoteAnnouncements()
                    remoteAnn.forEach { repository.insertAnnouncement(it) }
                }
            } catch (e: java.lang.Exception) {
                Log.e("ZuuViewModel", "Auto background sync failed", e)
            }
        }
    }

    fun syncWithSupabase(onComplete: (Boolean, String?) -> Unit = { _, _ -> }) {
        if (!supabaseClient.isConfigured) {
            onComplete(false, "Supabase URL atau API Key belum diset.")
            return
        }
        viewModelScope.launch {
            isSyncing.value = true
            syncErrorMessage.value = null
            try {
                withContext(Dispatchers.IO) {
                    val remoteUsers = supabaseClient.getRemoteUsers()
                    remoteUsers.forEach { repository.insertUser(it) }

                    val remoteTasks = supabaseClient.getRemoteTasks()
                    remoteTasks.forEach { repository.insertTask(it) }

                    val remoteSubmissions = supabaseClient.getRemoteSubmissions()
                    remoteSubmissions.forEach { repository.insertSubmission(it) }

                    val remoteCheckins = supabaseClient.getRemoteCheckIns()
                    remoteCheckins.forEach { repository.insertCheckIn(it) }

                    val remoteWarnings = supabaseClient.getRemoteWarnings()
                    remoteWarnings.forEach { repository.insertWarning(it) }

                    val remoteNdp = supabaseClient.getRemoteNdp()
                    remoteNdp.forEach { repository.insertNdp(it) }

                    val remoteSalaries = supabaseClient.getRemoteSalaries()
                    remoteSalaries.forEach { repository.insertSalary(it) }

                    val remoteClaims = supabaseClient.getRemoteBonusClaims()
                    remoteClaims.forEach { repository.insertBonusClaim(it) }

                    val remotePayments = supabaseClient.getRemotePayments()
                    remotePayments.forEach { repository.insertPayment(it) }

                    val remoteAnnouncements = supabaseClient.getRemoteAnnouncements()
                    remoteAnnouncements.forEach { repository.insertAnnouncement(it) }

                    val remoteTickets = supabaseClient.getRemoteSupportTickets()
                    remoteTickets.forEach { repository.insertTicket(it) }
                }
                onComplete(true, null)
            } catch (e: Exception) {
                Log.e("ZuuViewModel", "Manual sync failure", e)
                syncErrorMessage.value = e.localizedMessage
                onComplete(false, e.localizedMessage)
            } finally {
                isSyncing.value = false
            }
        }
    }

    // --- User Actions ---

    // 1. Check in
    fun checkIn(employeeId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val calendar = java.util.Calendar.getInstance()
            val hour = calendar.get(java.util.Calendar.HOUR_OF_DAY)
            if (hour >= 12) {
                // Past 12:00 PM, do not insert check-in
                return@launch
            }
            val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val checkinsList = checkins.value
            val alreadyChecked = checkinsList.any { it.employeeId == employeeId && it.dateString == dateStr }
            if (!alreadyChecked) {
                repository.insertCheckIn(DailyCheckIn(employeeId = employeeId, dateString = dateStr))
                logActivity(employeeId, "Check-In", "Melakukan check-in harian sukses pada tanggal $dateStr.")
            }
        }
    }

    // 2. Register
    fun registerEmployee(fullName: String, whatsappNumber: String, psw: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val existing = repository.getUserByWhatsApp(whatsappNumber)
            if (existing != null) {
                withContext(Dispatchers.Main) {
                    onResult(false)
                }
                return@launch
            }
            val count = repository.getUserCount()
            val formatId = "EMP-${String.format("%06d", count + 1)}"
            val roleName = "Employee"
            val statusName = "Pending"

            val newUser = User(
                fullName = fullName,
                whatsappNumber = whatsappNumber,
                passwordHash = psw, 
                role = roleName,
                employeeId = formatId,
                status = statusName
            )
            repository.insertUser(newUser)
            if (supabaseClient.isConfigured) {
                supabaseClient.upsertRemoteUser(newUser)
            }
            
            registeredEmployeeId.value = formatId
            registrationSuccessMessage.value = "Pendaftaran berhasil"
            
            logActivity(formatId, "Registration", "Mendaftar akun baru dengan ID $formatId.")
            withContext(Dispatchers.Main) {
                onResult(true)
            }
        }
    }

    // 3. Login
    fun login(whatsappNumber: String, psw: String, onResult: (String?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            var user = if (supabaseClient.isConfigured) {
                supabaseClient.getRemoteUserByWhatsApp(whatsappNumber)?.also {
                    // Cache locally
                    repository.insertUser(it)
                }
            } else {
                null
            }

            if (user == null) {
                user = repository.getUserByWhatsApp(whatsappNumber)
            }

            if (user == null || user.passwordHash != psw) {
                withContext(Dispatchers.Main) {
                    onResult("WhatsApp atau Password salah.")
                }
                return@launch
            }
            
            // Save to preferences on successful login
            appPrefs.edit()
                .putString("logged_in_wa", whatsappNumber)
                .putString("logged_in_pw", psw)
                .apply()

            _currentUser.value = user
            logActivity(user.employeeId, "Login", "Berhasil masuk ke dalam aplikasi.")

            withContext(Dispatchers.Main) {
                when (user.status) {
                    "Pending" -> _currentScreen.value = Screen.PendingApproval
                    "Rejected" -> _currentScreen.value = Screen.Rejected
                    "Suspended" -> _currentScreen.value = Screen.Suspended
                    "Active" -> {
                        when (user.role) {
                            "Owner" -> _currentScreen.value = Screen.OwnerDashboard
                            "Admin" -> _currentScreen.value = Screen.AdminDashboard
                            "Service Engineer" -> _currentScreen.value = Screen.ServiceEngineerDashboard
                            else -> {
                                if (user.ms001Username.isBlank() || user.ms001ReferralLink.isBlank()) {
                                    _currentScreen.value = Screen.Ms001Registration
                                } else {
                                    _currentScreen.value = Screen.EmployeeDashboard
                                }
                            }
                        }
                    }
                }
                onResult(null)
            }
        }
    }

    // Logout
    fun logout() {
        viewModelScope.launch {
            // Clear credentials from preferences
            appPrefs.edit()
                .remove("logged_in_wa")
                .remove("logged_in_pw")
                .apply()

            _currentUser.value?.let { user ->
                withContext(Dispatchers.IO) {
                    logActivity(user.employeeId, "Logout", "Menutup sesi aplikasi.")
                }
            }
            _currentUser.value = null
            _currentScreen.value = Screen.Welcome
        }
    }

    fun registerMs001Account(username: String, referralLink: String, onComplete: (Boolean, String?) -> Unit) {
        val user = _currentUser.value ?: return
        viewModelScope.launch(Dispatchers.IO) {
            val updated = user.copy(
                ms001Username = username.trim(),
                ms001ReferralLink = referralLink.trim()
            )
            repository.insertUser(updated)
            if (supabaseClient.isConfigured) {
                try {
                    supabaseClient.upsertRemoteUser(updated)
                } catch (e: Exception) {
                    Log.e("BarbarViewModel", "Failed to sync BARBAR365 info to Supabase", e)
                }
            }
            _currentUser.value = updated
            logActivity(updated.employeeId, "BARBAR365 Registration", "Registrasi website ms001.barbar365a.site berhasil (ID: $username)")
            withContext(Dispatchers.Main) {
                _currentScreen.value = Screen.EmployeeDashboard
                onComplete(true, null)
            }
        }
    }

    // Force purge and database cleaning
    fun purgeAndResetDatabase(onComplete: () -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.clearAllData()
            }
            appPrefs.edit()
                .remove("logged_in_wa")
                .remove("logged_in_pw")
                .apply()
            _currentUser.value = null
            _currentScreen.value = Screen.Welcome
            onComplete()
        }
    }

    // --- Owner: Admin Management Panel ---
    fun ownerCreateAdmin(fullName: String, whatsappNumber: String, psw: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val existing = repository.getUserByWhatsApp(whatsappNumber)
            if (existing != null) {
                withContext(Dispatchers.Main) {
                    onResult(false, "Nomor WhatsApp sudah terdaftar!")
                }
                return@launch
            }
            val count = repository.getUserCount()
            val formatId = "ADM-${String.format("%04d", count + 1)}"
            val adminUser = User(
                fullName = fullName,
                whatsappNumber = whatsappNumber,
                passwordHash = psw,
                role = "Admin",
                employeeId = formatId,
                status = "Active"
            )
            repository.insertUser(adminUser)
            if (supabaseClient.isConfigured) {
                supabaseClient.upsertRemoteUser(adminUser)
            }
            logActivity(currentUser.value?.employeeId ?: "OWNER", "Owner Created Admin", "Owner membuat Admin baru: $fullName (ID: $formatId)")
            withContext(Dispatchers.Main) {
                onResult(true, null)
            }
        }
    }

    fun ownerUpdateAdmin(admin: User, newName: String, newWhatsapp: String, newPsw: String, newStatus: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val existing = repository.getUserByWhatsApp(newWhatsapp)
            if (existing != null && existing.id != admin.id) {
                withContext(Dispatchers.Main) {
                    onResult(false, "Nomor WhatsApp sudah digunakan oleh akun lain!")
                }
                return@launch
            }
            val updated = admin.copy(
                fullName = newName,
                whatsappNumber = newWhatsapp,
                passwordHash = newPsw,
                status = newStatus
            )
            repository.updateUser(updated)
            if (supabaseClient.isConfigured) {
                supabaseClient.upsertRemoteUser(updated)
            }
            logActivity(currentUser.value?.employeeId ?: "OWNER", "Owner Updated Admin", "Owner mengubah info Admin ${admin.employeeId}: $newName, Status: $newStatus")
            withContext(Dispatchers.Main) {
                onResult(true, null)
            }
        }
    }

    fun ownerDeleteAdmin(admin: User, onResult: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(admin)
            logActivity(currentUser.value?.employeeId ?: "OWNER", "Owner Deleted Admin", "Owner menghapus Admin ${admin.employeeId}: ${admin.fullName}")
            withContext(Dispatchers.Main) {
                onResult(true)
            }
        }
    }

    // 4. Navigation
    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }

    // --- Task Actions (Admin) ---
    fun createTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(task)
            currentUser.value?.let {
                logActivity(it.employeeId, "Create Task", "Membuat tugas baru: ${task.title}")
            }
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
            currentUser.value?.let {
                logActivity(it.employeeId, "Update Task", "Mengubah rincian tugas: ${task.title}")
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
            currentUser.value?.let {
                logActivity(it.employeeId, "Delete Task", "Menghapus tugas: ${task.title}")
            }
        }
    }

    fun toggleTaskVisibility(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            val updated = task.copy(isHidden = !task.isHidden)
            repository.updateTask(updated)
            currentUser.value?.let {
                val actionText = if (updated.isHidden) "Menyembunyikan" else "Menampilkan"
                logActivity(it.employeeId, "$actionText Task", "$actionText tugas: ${task.title}")
            }
        }
    }

    // --- Task Submission (Employee) ---
    fun submitTaskProof(
        taskId: Long,
        taskTitle: String,
        platform: String,
        postLink: String,
        screenshot: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = _currentUser.value ?: return@launch
            val sub = TaskSubmission(
                taskId = taskId,
                taskTitle = taskTitle,
                employeeId = user.employeeId,
                employeeName = user.fullName,
                platform = platform,
                postLink = postLink,
                screenshotPath = screenshot,
                status = "Pending Review"
            )
            repository.insertSubmission(sub)
            logActivity(user.employeeId, "Task Submission", "Mengirimkan bukti tugas untuk '$taskTitle' di $platform.")
        }
    }

    // --- Submission Review (Admin) ---
    fun reviewSubmission(submission: TaskSubmission, isApproved: Boolean, rejectReason: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedStatus = if (isApproved) "Approved" else "Rejected"
            val updatedSub = submission.copy(status = updatedStatus, rejectionReason = rejectReason)
            repository.updateSubmission(updatedSub)

            currentUser.value?.let { admin ->
                logActivity(
                    admin.employeeId,
                    "Submission Review",
                    "Memproses peninjauan tugas karyawan ${submission.employeeId}: status $updatedStatus."
                )
            }
        }
    }

    // --- Admin: Update Employee Status & Warning Levels ---
    fun updateEmployeeStatus(employee: User, status: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val updated = employee.copy(status = status)
            repository.updateUser(updated)
            currentUser.value?.let { admin ->
                logActivity(admin.employeeId, "Employee Status Edit", "Mengubah status karyawan ${employee.employeeId} menjadi $status.")
            }
        }
    }

    fun issueWarning(employee: User, level: Int, reason: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // Update Warning level for user
            val updatedUser = employee.copy(warningLevel = level)
            repository.updateUser(updatedUser)

            // Save warning log
            val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val warn = Warning(
                employeeId = employee.employeeId,
                employeeName = employee.fullName,
                level = level,
                reason = reason,
                dateString = todayStr
            )
            repository.insertWarning(warn)

            currentUser.value?.let { admin ->
                logActivity(admin.employeeId, "Issue Warning", "Menerbitkan Peringatan SP $level ke ${employee.fullName} (ID: ${employee.employeeId}).")
            }
        }
    }

    // --- Admin: NDP Tracking Updates ---
    fun updateWeeklyNdp(employeeId: String, score: Int, weekNo: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val ndp = WeeklyNdp(employeeId = employeeId, weekNumber = weekNo, ndpScore = score)
            repository.insertNdp(ndp)

            // Auto NDP bonus detection: if NDP >= 21 in this week and employee has no existing NDP Bonus for this week
            if (score >= 21) {
                // Check if payment already exists for this employee for NDP Bonus
                val paymentsList = repository.allPayments.firstOrNull() ?: emptyList()
                val hasBonus = paymentsList.any { 
                    it.employeeId == employeeId && 
                    it.paymentType == "NDP Bonus" && 
                    it.dateString == "Minggu $weekNo" 
                }
                if (!hasBonus) {
                    val employee = repository.getUserByEmployeeId(employeeId)
                    val empName = employee?.fullName ?: "Karyawan"
                    val bonusPayment = Payment(
                        employeeId = employeeId,
                        employeeName = empName,
                        amount = 200000.0,
                        paymentType = "NDP Bonus",
                        status = "Pending",
                        dateString = "Minggu $weekNo"
                    )
                    repository.insertPayment(bonusPayment)
                    logActivity("SYSTEM", "Auto NDP Bonus", "Mendeteksi secara otomatis kelayakan Bonus NDP >= 21 untuk $empName.")
                }
            }

            currentUser.value?.let { admin ->
                logActivity(admin.employeeId, "NDP Tracker Set", "Menambahkan/mengubah NDP pekan ke-$weekNo untuk karyawan $employeeId menjadi $score.")
            }
        }
    }

    // --- FYP Bonus Claims (Employee & Admin) ---
    fun getFypRecommendedReward(views: Long, ndp: Int): Double {
        return when {
            views >= 200000 && ndp >= 20 -> 200000.0
            views >= 150000 && ndp >= 15 -> 150000.0
            views >= 100000 && ndp >= 10 -> 100000.0
            views >= 50000 && ndp >= 5 -> 50000.0
            else -> 0.0
        }
    }

    fun claimFypBonus(platformLink: String, screenshotInsights: String, views: Long, currentWeeklyNdp: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = _currentUser.value ?: return@launch
            val recommended = getFypRecommendedReward(views, currentWeeklyNdp)
            val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val claim = BonusClaim(
                employeeId = user.employeeId,
                employeeName = user.fullName,
                platformLink = platformLink,
                screenshotInsights = screenshotInsights,
                viewCount = views,
                recommendedReward = recommended,
                approvedReward = 0.0, // set on approval
                status = "Pending Review",
                dateString = todayStr
            )
            repository.insertBonusClaim(claim)
            logActivity(user.employeeId, "Claim FYP Bonus", "Mengajukan klaim bonus FYP dengan konten berikhtisar $views tayangan.")
        }
    }

    fun reviewBonusClaim(claim: BonusClaim, isApproved: Boolean, approvedAmount: Double, reason: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedStatus = if (isApproved) "Approved" else "Rejected"
            val processedClaim = claim.copy(
                status = updatedStatus,
                approvedReward = if (isApproved) approvedAmount else 0.0,
                reason = reason
            )
            repository.updateBonusClaim(processedClaim)

            // If approved, dynamically insert a corresponding Payment record
            if (isApproved) {
                val pay = Payment(
                    employeeId = claim.employeeId,
                    employeeName = claim.employeeName,
                    amount = approvedAmount,
                    paymentType = "FYP Bonus",
                    status = "Approved",
                    dateString = claim.dateString
                )
                repository.insertPayment(pay)
            }

            currentUser.value?.let { admin ->
                logActivity(admin.employeeId, "Bonus Review", "Membuka dan memproses klaim bonus FYP karyawan ${claim.employeeId}: status $updatedStatus.")
            }
        }
    }

    fun updatePayment(payment: Payment) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePayment(payment)
            currentUser.value?.let { admin ->
                logActivity(admin.employeeId, "Update Payment", "Mengubah status pembayaran Rp ${payment.amount} menjadi ${payment.status}.")
            }
        }
    }

    // --- Salary Payments Management (Admin & Employee) ---
    fun calculateSalaryEligibility(
        employeeId: String,
        employeeName: String,
        weekTasks: List<Task>,
        employeeSubmissions: List<TaskSubmission>,
        employeeCheckins: List<DailyCheckIn>,
        employeeWarnings: List<Warning>,
        suspended: Boolean
    ): String {
        if (suspended) return "Not Eligible"

        // Rule details:
        // 1. Daily check-ins completed (at least 1 check-in for active weekly flow)
        val checkInCompleted = employeeCheckins.isNotEmpty()
        if (!checkInCompleted) return "Not Eligible"

        // 2. Minimum task completion percentage
        val totalTasks = weekTasks.size
        val approvedSubmissions = employeeSubmissions.count { it.status == "Approved" }
        val completionRate = if (totalTasks > 0) (approvedSubmissions * 100) / totalTasks else 100
        val minRate = minCompletionRate.value
        if (completionRate < minRate) return "Not Eligible"

        // 3. No excessive rejected submissions
        val rejectedCount = employeeSubmissions.count { it.status == "Rejected" }
        val maxRejections = maxRejectionCount.value
        if (rejectedCount > maxRejections) return "Not Eligible"

        // 4. Not suspended (warning level < 3)
        // Handled under suspended check

        return "Eligible"
    }

    fun approveSalaryRecord(employeeId: String, employeeName: String, weekNo: Int, isEligible: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val amount = if (isEligible) 100000.0 else 0.0
            val status = if (isEligible) "Approved" else "Rejected"
            val salary = SalaryRecord(
                employeeId = employeeId,
                employeeName = employeeName,
                weekNumber = weekNo,
                amount = amount,
                status = status
            )
            repository.insertSalary(salary)

            if (isEligible) {
                // Create a payment entry too
                val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val pay = Payment(
                    employeeId = employeeId,
                    employeeName = employeeName,
                    amount = amount,
                    paymentType = "Weekly Salary",
                    status = "Approved",
                    dateString = todayStr
                )
                repository.insertPayment(pay)
            }

            currentUser.value?.let { admin ->
                logActivity(
                    admin.employeeId,
                    "Salary Approval",
                    "Memproses kelayakan gaji karyawan $employeeName pekan-$weekNo: status $status."
                )
            }
        }
    }

    fun markSalaryPaid(salary: SalaryRecord) {
        viewModelScope.launch(Dispatchers.IO) {
            val updated = salary.copy(status = "Paid", processedAt = System.currentTimeMillis())
            repository.updateSalary(updated)

            // Also find counterpart payment if any and mark as Paid
            val paymentsList = repository.allPayments.firstOrNull() ?: emptyList()
            val pt = paymentsList.find { it.employeeId == salary.employeeId && it.paymentType == "Weekly Salary" && it.status == "Approved" }
            if (pt != null) {
                repository.updatePayment(pt.copy(status = "Paid"))
            }

            currentUser.value?.let { admin ->
                logActivity(admin.employeeId, "Mark Gaji Paid", "Menandai pembayaran gaji karyawan ${salary.employeeName} lunas.")
            }
        }
    }

    // --- Admin: Manually Add Referral / Other Reward ---
    fun addManualPayment(employeeId: String, employeeName: String, amount: Double, type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val pay = Payment(
                employeeId = employeeId,
                employeeName = employeeName,
                amount = amount,
                paymentType = type,
                status = "Paid", // automatically paid on manual issuance
                dateString = todayStr
            )
            repository.insertPayment(pay)

            currentUser.value?.let { admin ->
                logActivity(admin.employeeId, "Add Manual Reward", "Menerbitkan pembayaran manual jenis $type sebesar Rp $amount ke $employeeName.")
            }
        }
    }

    // --- Announcement Actions ---
    fun createAnnouncement(ann: Announcement) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertAnnouncement(ann)
            currentUser.value?.let { admin ->
                logActivity(admin.employeeId, "Create Announcement", "Membuat pengumuman baru: ${ann.title}.")
            }
        }
    }

    fun deleteAnnouncement(ann: Announcement) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAnnouncement(ann)
            currentUser.value?.let { admin ->
                logActivity(admin.employeeId, "Delete Announcement", "Menghapus pengumuman: ${ann.title}.")
            }
        }
    }

    fun claimAnnouncementAck(announcementId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = _currentUser.value ?: return@launch
            val ack = AnnouncementAck(announcementId = announcementId, employeeId = user.employeeId)
            repository.insertAck(ack)
            logActivity(user.employeeId, "Acknowledge Announcement", "Membaca dan menandai pengumuman ID $announcementId dibaca.")
        }
    }

    // --- Support Tickets (Help Center) ---
    fun createSupportTicket(title: String, desc: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = _currentUser.value ?: return@launch
            val ticket = SupportTicket(
                employeeId = user.employeeId,
                employeeName = user.fullName,
                title = title,
                description = desc,
                status = "Open"
            )
            repository.insertTicket(ticket)
            logActivity(user.employeeId, "Open Support Ticket", "Membuat tiket keluhan bantuan: '$title'.")
        }
    }

    fun replyToTicket(ticket: SupportTicket, replyContent: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val updated = ticket.copy(reply = replyContent, status = "Replied")
            repository.updateTicket(updated)

            currentUser.value?.let { admin ->
                logActivity(admin.employeeId, "Reply Ticket", "Membalas tiket konsultasi bantuan dari karyawan ${ticket.employeeId}.")
            }
        }
    }

    // --- Helper function for Activity Logs ---
    fun logActivity(employeeId: String, action: String, details: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val log = ActivityLog(employeeId = employeeId, action = action, details = details)
            repository.insertLog(log)
        }
    }

    // --- Profile Update Actions ---
    fun updateEmployeeProfile(
        fullName: String,
        whatsappNumber: String,
        telegramUsername: String,
        bankName: String,
        bankAccountNumber: String,
        bankAccountName: String,
        profilePictureUrl: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = _currentUser.value ?: return@launch
            val updatedUser = user.copy(
                fullName = fullName,
                whatsappNumber = whatsappNumber,
                telegramUsername = telegramUsername,
                bankName = bankName,
                bankAccountNumber = bankAccountNumber,
                bankAccountName = bankAccountName,
                profilePictureUrl = profilePictureUrl
            )
            repository.updateUser(updatedUser)
            _currentUser.value = updatedUser
            logActivity(user.employeeId, "Profile Update", "Memperbarui data profil: Nama, Kontak, Rekening Bank, atau Foto Profil.")
        }
    }

    // --- Day of Week Calculation ---
    fun getJoinDayOfWeek(registeredAt: Long): String {
        val date = Date(registeredAt)
        val dayFormat = SimpleDateFormat("EEEE", Locale.ENGLISH).format(date)
        return when (dayFormat) {
            "Monday" -> "Senin"
            "Tuesday" -> "Selasa"
            "Wednesday" -> "Rabu"
            "Thursday" -> "Kamis"
            "Friday" -> "Jumat"
            "Saturday" -> "Sabtu"
            "Sunday" -> "Minggu"
            else -> dayFormat
        }
    }

    // --- Employee Wallet & Withdrawal Requests ---
    fun requestWithdrawal(amount: Double, onError: (String) -> Unit, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val currUser = _currentUser.value
            if (currUser == null || currUser.role != "Employee") {
                onError("Hanya karyawan yang dapat menarik dana.")
                return@launch
            }
            if (currUser.bankName.isBlank() || currUser.bankAccountNumber.isBlank() || currUser.bankAccountName.isBlank()) {
                onError("Silakan lengkapi informasi Rekening/E-Wallet Anda di profil terlebih dahulu.")
                return@launch
            }
            if (amount <= 0) {
                onError("Jumlah penarikan harus lebih dari Rp 0.")
                return@launch
            }
            if (currUser.walletBalance < amount) {
                val formattedBal = String.format("%,.0f", currUser.walletBalance)
                onError("Saldo dompet Anda tidak mencukupi (Saldo: Rp $formattedBal).")
                return@launch
            }

            // Deduct immediately and insert request
            val updatedUser = currUser.copy(walletBalance = currUser.walletBalance - amount)
            repository.updateUser(updatedUser)
            _currentUser.value = updatedUser

            val request = WithdrawalRequest(
                employeeId = currUser.employeeId,
                employeeName = currUser.fullName,
                amount = amount,
                status = "Pending",
                bankName = currUser.bankName,
                bankAccountNumber = currUser.bankAccountNumber,
                bankAccountName = currUser.bankAccountName
            )
            repository.insertWithdrawalRequest(request)
            
            val formattedAmount = String.format("%,.0f", amount)
            logActivity(currUser.employeeId, "Withdrawal Request", "Mengajukan penarikan dana sebesar Rp $formattedAmount.")
            onSuccess()
        }
    }

    // --- Admin/Owner: Play Salary to Employee & Add to Wallet ---
    fun paySalaryToEmployee(employeeId: String, amount: Double, reason: String = "Gaji Mingguan", onSuccess: () -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            val emp = repository.getUserByEmployeeId(employeeId)
            if (emp == null) {
                return@launch
            }

            // Record in payments list as Status: Paid (or Approved, but immediately Paid is standard)
            val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val pay = Payment(
                employeeId = employeeId,
                employeeName = emp.fullName,
                amount = amount,
                paymentType = "Weekly Salary", // Match standard types
                status = "Paid",
                dateString = todayStr
            )
            repository.insertPayment(pay)

            // Update employee's wallet
            val updatedEmp = emp.copy(walletBalance = emp.walletBalance + amount)
            repository.updateUser(updatedEmp)

            // If current user is this employee, update their state
            if (_currentUser.value?.employeeId == employeeId) {
                _currentUser.value = updatedEmp
            }

            // Log activity
            val admin = _currentUser.value
            if (admin != null) {
                val formattedAmount = String.format("%,.0f", amount)
                logActivity(admin.employeeId, "Pay Salary", "Membayar $reason kepada ${emp.fullName} sebesar Rp $formattedAmount.")
            }
            
            onSuccess()
        }
    }

    // --- Admin/Owner: Process Withdrawal Request ---
    fun approveWithdrawal(requestId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val requestsList = withdrawalRequests.value
            val req = requestsList.find { it.id == requestId } ?: return@launch
            
            // Mark as Approved
            val updatedReq = req.copy(status = "Approved")
            repository.updateWithdrawalRequest(updatedReq)

            // Record as custom Payment (cash output)
            val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val pay = Payment(
                employeeId = req.employeeId,
                employeeName = req.employeeName,
                amount = req.amount,
                paymentType = "Withdrawal",
                status = "Paid",
                dateString = todayStr
            )
            repository.insertPayment(pay)

            val admin = _currentUser.value
            if (admin != null) {
                val formattedAmount = String.format("%,.0f", req.amount)
                logActivity(admin.employeeId, "Withdrawal Approved", "Menyetujui pencairan penarikan dana ${req.employeeName} sebesar Rp $formattedAmount.")
            }
        }
    }

    fun rejectWithdrawal(requestId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val requestsList = withdrawalRequests.value
            val req = requestsList.find { it.id == requestId } ?: return@launch

            // Mark as Rejected
            val updatedReq = req.copy(status = "Rejected")
            repository.updateWithdrawalRequest(updatedReq)

            // Refund the balance
            val emp = repository.getUserByEmployeeId(req.employeeId)
            if (emp != null) {
                val updatedEmp = emp.copy(walletBalance = emp.walletBalance + req.amount)
                repository.updateUser(updatedEmp)
                if (_currentUser.value?.employeeId == emp.employeeId) {
                    _currentUser.value = updatedEmp
                }
            }

            val admin = _currentUser.value
            if (admin != null) {
                val formattedAmount = String.format("%,.0f", req.amount)
                logActivity(admin.employeeId, "Withdrawal Rejected", "Menolak penarikan dana ${req.employeeName} sebesar Rp $formattedAmount (dana dikembalikan ke dompet).")
            }
        }
    }

    fun serviceEngineerUpdateUser(user: User, newRole: String, newStatus: String, newWarningLevel: Int, onComplete: () -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            val updated = user.copy(role = newRole, status = newStatus, warningLevel = newWarningLevel)
            repository.updateUser(updated)
            if (supabaseClient.isConfigured) {
                supabaseClient.upsertRemoteUser(updated)
            }
            logActivity(currentUser.value?.employeeId ?: "ENGINEER", "Engineer Update User", "Engineer mengubah user ${user.employeeId}: Role=$newRole, Status=$newStatus, Warning=$newWarningLevel")
            withContext(Dispatchers.Main) {
                onComplete()
            }
        }
    }
}

class ZuuViewModelFactory(private val repository: ZuuRepository, private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ZuuViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ZuuViewModel(repository, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
