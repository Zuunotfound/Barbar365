package com.example.data

import kotlinx.coroutines.flow.Flow

class ZuuRepository(private val dao: ZuuDao) {

    // --- Users ---
    val allUsers: Flow<List<User>> = dao.getAllUsersFlow()
    
    suspend fun insertUser(user: User) = dao.insertUser(user)
    suspend fun updateUser(user: User) = dao.updateUser(user)
    suspend fun deleteUser(user: User) = dao.deleteUser(user)
    suspend fun getUserByWhatsApp(wa: String): User? = dao.getUserByWhatsApp(wa)
    suspend fun getUserByEmployeeId(empId: String): User? = dao.getUserByEmployeeId(empId)
    suspend fun getAllUsersList(): List<User> = dao.getAllUsers()
    suspend fun getUserCount(): Int = dao.getUserCount()

    // --- Tasks ---
    val allTasks: Flow<List<Task>> = dao.getAllTasksFlow()
    val activeTasks: Flow<List<Task>> = dao.getActiveTasksFlow()
    
    suspend fun insertTask(task: Task) = dao.insertTask(task)
    suspend fun updateTask(task: Task) = dao.updateTask(task)
    suspend fun deleteTask(task: Task) = dao.deleteTask(task)

    // --- Submissions ---
    val allSubmissions: Flow<List<TaskSubmission>> = dao.getAllSubmissionsFlow()
    
    fun getSubmissionsForEmployee(empId: String): Flow<List<TaskSubmission>> = 
        dao.getSubmissionsForEmployeeFlow(empId)
        
    suspend fun insertSubmission(submission: TaskSubmission) = dao.insertSubmission(submission)
    suspend fun updateSubmission(submission: TaskSubmission) = dao.updateSubmission(submission)

    // --- Daily Check-Ins ---
    val allCheckIns: Flow<List<DailyCheckIn>> = dao.getAllCheckInsFlow()
    
    fun getCheckInsForEmployee(empId: String): Flow<List<DailyCheckIn>> =
        dao.getCheckInsForEmployeeFlow(empId)
        
    suspend fun insertCheckIn(checkIn: DailyCheckIn) = dao.insertCheckIn(checkIn)

    // --- Warnings ---
    val allWarnings: Flow<List<Warning>> = dao.getAllWarningsFlow()
    
    fun getWarningsForEmployee(empId: String): Flow<List<Warning>> =
        dao.getWarningsForEmployeeFlow(empId)
        
    suspend fun insertWarning(warning: Warning) = dao.insertWarning(warning)

    // --- NDP ---
    val allNdp: Flow<List<WeeklyNdp>> = dao.getAllNdpFlow()
    
    fun getNdpForEmployee(empId: String): Flow<List<WeeklyNdp>> =
        dao.getNdpForEmployeeFlow(empId)
        
    suspend fun insertNdp(ndp: WeeklyNdp) = dao.insertNdp(ndp)

    // --- Salary ---
    val allSalaries: Flow<List<SalaryRecord>> = dao.getAllSalariesFlow()
    
    fun getSalariesForEmployee(empId: String): Flow<List<SalaryRecord>> =
        dao.getSalariesForEmployeeFlow(empId)
        
    suspend fun insertSalary(salary: SalaryRecord) = dao.insertSalary(salary)
    suspend fun updateSalary(salary: SalaryRecord) = dao.updateSalary(salary)

    // --- Bonus Claims ---
    val allBonusClaims: Flow<List<BonusClaim>> = dao.getAllBonusClaimsFlow()
    
    fun getBonusClaimsForEmployee(empId: String): Flow<List<BonusClaim>> =
        dao.getBonusClaimsForEmployeeFlow(empId)
        
    suspend fun insertBonusClaim(claim: BonusClaim) = dao.insertBonusClaim(claim)
    suspend fun updateBonusClaim(claim: BonusClaim) = dao.updateBonusClaim(claim)

    // --- Payments ---
    val allPayments: Flow<List<Payment>> = dao.getAllPaymentsFlow()
    
    fun getPaymentsForEmployee(empId: String): Flow<List<Payment>> =
        dao.getPaymentsForEmployeeFlow(empId)
        
    suspend fun insertPayment(payment: Payment) = dao.insertPayment(payment)
    suspend fun updatePayment(payment: Payment) = dao.updatePayment(payment)

    // --- Announcements ---
    val allAnnouncements: Flow<List<Announcement>> = dao.getAllAnnouncementsFlow()
    
    suspend fun insertAnnouncement(announcement: Announcement) = dao.insertAnnouncement(announcement)
    suspend fun updateAnnouncement(announcement: Announcement) = dao.updateAnnouncement(announcement)
    suspend fun deleteAnnouncement(announcement: Announcement) = dao.deleteAnnouncement(announcement)

    // --- Announcement Acks ---
    suspend fun insertAck(ack: AnnouncementAck) = dao.insertAck(ack)
    suspend fun getAcksForEmployee(empId: String): List<AnnouncementAck> = dao.getAcksForEmployee(empId)

    // --- Support Tickets ---
    val allTickets: Flow<List<SupportTicket>> = dao.getAllTicketsFlow()
    
    fun getTicketsForEmployee(empId: String): Flow<List<SupportTicket>> =
        dao.getTicketsForEmployeeFlow(empId)
        
    suspend fun insertTicket(ticket: SupportTicket) = dao.insertTicket(ticket)
    suspend fun updateTicket(ticket: SupportTicket) = dao.updateTicket(ticket)

    // --- Logs ---
    val allLogs: Flow<List<ActivityLog>> = dao.getAllLogsFlow()
    
    suspend fun insertLog(log: ActivityLog) = dao.insertLog(log)

    // --- Withdrawal Requests ---
    val allWithdrawalRequests: Flow<List<WithdrawalRequest>> = dao.getAllWithdrawalRequestsFlow()

    fun getWithdrawalRequestsForEmployee(empId: String): Flow<List<WithdrawalRequest>> =
        dao.getWithdrawalRequestsForEmployeeFlow(empId)

    suspend fun insertWithdrawalRequest(req: WithdrawalRequest) = dao.insertWithdrawalRequest(req)
    suspend fun updateWithdrawalRequest(req: WithdrawalRequest) = dao.updateWithdrawalRequest(req)

    suspend fun clearAllData() {
        dao.clearUsersTable()
        dao.clearTasksTable()
        dao.clearSubmissionsTable()
        dao.clearCheckinsTable()
        dao.clearWarningsTable()
        dao.clearNdpTable()
        dao.clearSalariesTable()
        dao.clearClaimsTable()
        dao.clearPaymentsTable()
        dao.clearAnnouncementsTable()
        dao.clearAcksTable()
        dao.clearTicketsTable()
        dao.clearLogsTable()
        dao.clearWithdrawalsTable()
    }
}
