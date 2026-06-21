package com.example.data

import android.content.Context
import android.util.Log
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SupabaseClient(context: Context) {

    private val sharedPrefs = context.getSharedPreferences("supabase_config", Context.MODE_PRIVATE)
    private val client = OkHttpClient()

    var supabaseUrl: String
        get() = sharedPrefs.getString("url", "") ?: ""
        set(value) = sharedPrefs.edit().putString("url", value.trim()).apply()

    var supabaseKey: String
        get() = sharedPrefs.getString("key", "") ?: ""
        set(value) = sharedPrefs.edit().putString("key", value.trim()).apply()

    val isConfigured: Boolean
        get() = supabaseUrl.isNotEmpty() && supabaseKey.isNotEmpty()

    private fun buildRequest(urlPath: String, method: String, bodyJson: String? = null): Request? {
        val baseUrl = supabaseUrl.removeSuffix("/")
        val key = supabaseKey
        if (baseUrl.isEmpty() || key.isEmpty()) return null

        val builder = Request.Builder()
            .url("$baseUrl/rest/v1/$urlPath")
            .header("apikey", key)
            .header("Authorization", "Bearer $key")
            .header("Content-Type", "application/json")
            .header("Prefer", "return=representation")

        if (method == "POST" && bodyJson != null) {
            val mediaType = "application/json; charset=utf-8".toMediaType()
            builder.post(bodyJson.toRequestBody(mediaType))
        } else if (method == "PATCH" && bodyJson != null) {
            val mediaType = "application/json; charset=utf-8".toMediaType()
            builder.patch(bodyJson.toRequestBody(mediaType))
        } else if (method == "DELETE") {
            builder.delete()
        }

        return builder.build()
    }

    private suspend fun executeCall(request: Request): String? = withContext(Dispatchers.IO) {
        try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    response.body?.string()
                } else {
                    Log.e("SupabaseClient", "API Error: ${response.code} ${response.message}")
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("SupabaseClient", "Network Call Exception", e)
            null
        }
    }

    // --- Users Table API ---
    suspend fun getRemoteUsers(): List<User> {
        val req = buildRequest("users?select=*", "GET") ?: return emptyList()
        val res = executeCall(req) ?: return emptyList()
        val list = mutableListOf<User>()
        try {
            val arr = JSONArray(res)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                list.add(obj.toUser())
            }
        } catch (e: Exception) {
            Log.e("SupabaseClient", "Mapping Users Failed", e)
        }
        return list
    }

    suspend fun getRemoteUserByWhatsApp(wa: String): User? {
        val req = buildRequest("users?whatsappNumber=eq.$wa&select=*", "GET") ?: return null
        val res = executeCall(req) ?: return null
        try {
            val arr = JSONArray(res)
            if (arr.length() > 0) {
                return arr.getJSONObject(0).toUser()
            }
        } catch (e: Exception) {
            Log.e("SupabaseClient", "Mapping User by WhatsApp Failed", e)
        }
        return null
    }

    suspend fun upsertRemoteUser(user: User): Boolean {
        val json = user.toJSONObject().toString()
        val req = buildRequest("users", "POST", json) ?: return false
        val res = executeCall(req)
        return res != null
    }

    suspend fun updateRemoteUser(user: User): Boolean {
        val json = user.toJSONObject().toString()
        val req = buildRequest("users?employeeId=eq.${user.employeeId}", "PATCH", json) ?: return false
        val res = executeCall(req)
        return res != null
    }

    // --- Tasks Table API ---
    suspend fun getRemoteTasks(): List<Task> {
        val req = buildRequest("tasks?select=*", "GET") ?: return emptyList()
        val res = executeCall(req) ?: return emptyList()
        val list = mutableListOf<Task>()
        try {
            val arr = JSONArray(res)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                list.add(obj.toTask())
            }
        } catch (e: Exception) {
            Log.e("SupabaseClient", "Mapping Tasks Failed", e)
        }
        return list
    }

    suspend fun upsertRemoteTask(task: Task): Boolean {
        val json = task.toJSONObject().toString()
        val req = buildRequest("tasks", "POST", json) ?: return false
        val res = executeCall(req)
        return res != null
    }

    suspend fun deleteRemoteTask(taskId: Long): Boolean {
        val req = buildRequest("tasks?id=eq.$taskId", "DELETE") ?: return false
        val res = executeCall(req)
        return res != null
    }

    // --- Submissions Table API ---
    suspend fun getRemoteSubmissions(): List<TaskSubmission> {
        val req = buildRequest("task_submissions?select=*", "GET") ?: return emptyList()
        val res = executeCall(req) ?: return emptyList()
        val list = mutableListOf<TaskSubmission>()
        try {
            val arr = JSONArray(res)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                list.add(obj.toTaskSubmission())
            }
        } catch (e: Exception) {
            Log.e("SupabaseClient", "Mapping Submissions Failed", e)
        }
        return list
    }

    suspend fun upsertRemoteSubmission(sub: TaskSubmission): Boolean {
        val json = sub.toJSONObject().toString()
        val req = buildRequest("task_submissions", "POST", json) ?: return false
        val res = executeCall(req)
        return res != null
    }

    suspend fun updateRemoteSubmission(sub: TaskSubmission): Boolean {
        val json = sub.toJSONObject().toString()
        val req = buildRequest("task_submissions?id=eq.${sub.id}", "PATCH", json) ?: return false
        val res = executeCall(req)
        return res != null
    }

    // --- Daily Check-Ins ---
    suspend fun getRemoteCheckIns(): List<DailyCheckIn> {
        val req = buildRequest("daily_checkins?select=*", "GET") ?: return emptyList()
        val res = executeCall(req) ?: return emptyList()
        val list = mutableListOf<DailyCheckIn>()
        try {
            val arr = JSONArray(res)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                list.add(obj.toDailyCheckIn())
            }
        } catch (e: Exception) {
            Log.e("SupabaseClient", "Mapping Checkins Failed", e)
        }
        return list
    }

    suspend fun upsertRemoteCheckIn(checkIn: DailyCheckIn): Boolean {
        val json = checkIn.toJSONObject().toString()
        val req = buildRequest("daily_checkins", "POST", json) ?: return false
        val res = executeCall(req)
        return res != null
    }

    // --- Warnings ---
    suspend fun getRemoteWarnings(): List<Warning> {
        val req = buildRequest("warnings?select=*", "GET") ?: return emptyList()
        val res = executeCall(req) ?: return emptyList()
        val list = mutableListOf<Warning>()
        try {
            val arr = JSONArray(res)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                list.add(obj.toWarning())
            }
        } catch (e: Exception) {
            Log.e("SupabaseClient", "Mapping Warnings Failed", e)
        }
        return list
    }

    suspend fun upsertRemoteWarning(warning: Warning): Boolean {
        val json = warning.toJSONObject().toString()
        val req = buildRequest("warnings", "POST", json) ?: return false
        val res = executeCall(req)
        return res != null
    }

    // --- Weekly Ndp ---
    suspend fun getRemoteNdp(): List<WeeklyNdp> {
        val req = buildRequest("weekly_ndp?select=*", "GET") ?: return emptyList()
        val res = executeCall(req) ?: return emptyList()
        val list = mutableListOf<WeeklyNdp>()
        try {
            val arr = JSONArray(res)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                list.add(obj.toWeeklyNdp())
            }
        } catch (e: Exception) {
            Log.e("SupabaseClient", "Mapping NDP Failed", e)
        }
        return list
    }

    suspend fun upsertRemoteNdp(ndp: WeeklyNdp): Boolean {
        val json = ndp.toJSONObject().toString()
        val req = buildRequest("weekly_ndp", "POST", json) ?: return false
        val res = executeCall(req)
        return res != null
    }

    // --- Salary Record ---
    suspend fun getRemoteSalaries(): List<SalaryRecord> {
        val req = buildRequest("salary_records?select=*", "GET") ?: return emptyList()
        val res = executeCall(req) ?: return emptyList()
        val list = mutableListOf<SalaryRecord>()
        try {
            val arr = JSONArray(res)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                list.add(obj.toSalaryRecord())
            }
        } catch (e: Exception) {
            Log.e("SupabaseClient", "Mapping Salaries Failed", e)
        }
        return list
    }

    suspend fun upsertRemoteSalary(salary: SalaryRecord): Boolean {
        val json = salary.toJSONObject().toString()
        val req = buildRequest("salary_records", "POST", json) ?: return false
        val res = executeCall(req)
        return res != null
    }

    suspend fun updateRemoteSalary(salary: SalaryRecord): Boolean {
        val json = salary.toJSONObject().toString()
        val req = buildRequest("salary_records?id=eq.${salary.id}", "PATCH", json) ?: return false
        val res = executeCall(req)
        return res != null
    }

    // --- Bonus Claim ---
    suspend fun getRemoteBonusClaims(): List<BonusClaim> {
        val req = buildRequest("bonus_claims?select=*", "GET") ?: return emptyList()
        val res = executeCall(req) ?: return emptyList()
        val list = mutableListOf<BonusClaim>()
        try {
            val arr = JSONArray(res)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                list.add(obj.toBonusClaim())
            }
        } catch (e: Exception) {
            Log.e("SupabaseClient", "Mapping Claims Failed", e)
        }
        return list
    }

    suspend fun upsertRemoteBonusClaim(claim: BonusClaim): Boolean {
        val json = claim.toJSONObject().toString()
        val req = buildRequest("bonus_claims", "POST", json) ?: return false
        val res = executeCall(req)
        return res != null
    }

    suspend fun updateRemoteBonusClaim(claim: BonusClaim): Boolean {
        val json = claim.toJSONObject().toString()
        val req = buildRequest("bonus_claims?id=eq.${claim.id}", "PATCH", json) ?: return false
        val res = executeCall(req)
        return res != null
    }

    // --- Payment ---
    suspend fun getRemotePayments(): List<Payment> {
        val req = buildRequest("payments?select=*", "GET") ?: return emptyList()
        val res = executeCall(req) ?: return emptyList()
        val list = mutableListOf<Payment>()
        try {
            val arr = JSONArray(res)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                list.add(obj.toPayment())
            }
        } catch (e: Exception) {
            Log.e("SupabaseClient", "Mapping Payments Failed", e)
        }
        return list
    }

    suspend fun upsertRemotePayment(payment: Payment): Boolean {
        val json = payment.toJSONObject().toString()
        val req = buildRequest("payments", "POST", json) ?: return false
        val res = executeCall(req)
        return res != null
    }

    // --- Announcement ---
    suspend fun getRemoteAnnouncements(): List<Announcement> {
        val req = buildRequest("announcements?select=*", "GET") ?: return emptyList()
        val res = executeCall(req) ?: return emptyList()
        val list = mutableListOf<Announcement>()
        try {
            val arr = JSONArray(res)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                list.add(obj.toAnnouncement())
            }
        } catch (e: Exception) {
            Log.e("SupabaseClient", "Mapping Announcements Failed", e)
        }
        return list
    }

    suspend fun upsertRemoteAnnouncement(ann: Announcement): Boolean {
        val json = ann.toJSONObject().toString()
        val req = buildRequest("announcements", "POST", json) ?: return false
        val res = executeCall(req)
        return res != null
    }

    suspend fun deleteRemoteAnnouncement(annId: Long): Boolean {
        val req = buildRequest("announcements?id=eq.$annId", "DELETE") ?: return false
        val res = executeCall(req)
        return res != null
    }

    // --- Support Ticket ---
    suspend fun getRemoteSupportTickets(): List<SupportTicket> {
        val req = buildRequest("support_tickets?select=*", "GET") ?: return emptyList()
        val res = executeCall(req) ?: return emptyList()
        val list = mutableListOf<SupportTicket>()
        try {
            val arr = JSONArray(res)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                list.add(obj.toSupportTicket())
            }
        } catch (e: Exception) {
            Log.e("SupabaseClient", "Mapping Support Tickets Failed", e)
        }
        return list
    }

    suspend fun upsertRemoteSupportTicket(ticket: SupportTicket): Boolean {
        val json = ticket.toJSONObject().toString()
        val req = buildRequest("support_tickets", "POST", json) ?: return false
        val res = executeCall(req)
        return res != null
    }

    suspend fun updateRemoteSupportTicket(ticket: SupportTicket): Boolean {
        val json = ticket.toJSONObject().toString()
        val req = buildRequest("support_tickets?id=eq.${ticket.id}", "PATCH", json) ?: return false
        val res = executeCall(req)
        return res != null
    }

    // --- Conversions ---

    private fun JSONObject.toUser() = User(
        id = optLong("id", 0),
        fullName = optString("fullName", ""),
        whatsappNumber = optString("whatsappNumber", ""),
        passwordHash = optString("passwordHash", ""),
        role = optString("role", "Employee"),
        employeeId = optString("employeeId", ""),
        status = optString("status", "Pending"),
        warningLevel = optInt("warningLevel", 0),
        registeredAt = optLong("registeredAt", System.currentTimeMillis()),
        telegramUsername = optString("telegramUsername", ""),
        bankName = optString("bankName", ""),
        bankAccountNumber = optString("bankAccountNumber", ""),
        bankAccountName = optString("bankAccountName", ""),
        profilePictureUrl = optString("profilePictureUrl", ""),
        ms001Username = optString("ms001Username", ""),
        ms001ReferralLink = optString("ms001ReferralLink", "")
    )

    private fun User.toJSONObject() = JSONObject().apply {
        // Omitting sequential autogenerated id to let Supabase write it
        put("fullName", fullName)
        put("whatsappNumber", whatsappNumber)
        put("passwordHash", passwordHash)
        put("role", role)
        put("employeeId", employeeId)
        put("status", status)
        put("warningLevel", warningLevel)
        put("registeredAt", registeredAt)
        put("telegramUsername", telegramUsername)
        put("bankName", bankName)
        put("bankAccountNumber", bankAccountNumber)
        put("bankAccountName", bankAccountName)
        put("profilePictureUrl", profilePictureUrl)
        put("ms001Username", ms001Username)
        put("ms001ReferralLink", ms001ReferralLink)
    }

    private fun JSONObject.toTask() = Task(
        id = optLong("id", 0),
        title = optString("title", ""),
        description = optString("description", ""),
        contentCategory = optString("contentCategory", "Instagram"),
        videoUrl = optString("videoUrl", ""),
        imageUrl = optString("imageUrl", ""),
        fileUrl = optString("fileUrl", ""),
        caption = optString("caption", ""),
        hashtags = optString("hashtags", ""),
        instructions = optString("instructions", ""),
        deadline = optLong("deadline", 0),
        isHidden = optBoolean("isHidden", false)
    )

    private fun Task.toJSONObject() = JSONObject().apply {
        put("title", title)
        put("description", description)
        put("contentCategory", contentCategory)
        put("videoUrl", videoUrl)
        put("imageUrl", imageUrl)
        put("fileUrl", fileUrl)
        put("caption", caption)
        put("hashtags", hashtags)
        put("instructions", instructions)
        put("deadline", deadline)
        put("isHidden", isHidden)
    }

    private fun JSONObject.toTaskSubmission() = TaskSubmission(
        id = optLong("id", 0),
        taskId = optLong("taskId", 0),
        taskTitle = optString("taskTitle", ""),
        employeeId = optString("employeeId", ""),
        employeeName = optString("employeeName", ""),
        platform = optString("platform", ""),
        postLink = optString("postLink", ""),
        screenshotPath = optString("screenshotPath", ""),
        submissionDate = optLong("submissionDate", System.currentTimeMillis()),
        status = optString("status", "Pending Review"),
        rejectionReason = optString("rejectionReason", "")
    )

    private fun TaskSubmission.toJSONObject() = JSONObject().apply {
        put("taskId", taskId)
        put("taskTitle", taskTitle)
        put("employeeId", employeeId)
        put("employeeName", employeeName)
        put("platform", platform)
        put("postLink", postLink)
        put("screenshotPath", screenshotPath)
        put("submissionDate", submissionDate)
        put("status", status)
        put("rejectionReason", rejectionReason)
    }

    private fun JSONObject.toDailyCheckIn() = DailyCheckIn(
        id = optLong("id", 0),
        employeeId = optString("employeeId", ""),
        dateString = optString("dateString", ""),
        checkinTime = optLong("checkinTime", System.currentTimeMillis())
    )

    private fun DailyCheckIn.toJSONObject() = JSONObject().apply {
        put("employeeId", employeeId)
        put("dateString", dateString)
        put("checkinTime", checkinTime)
    }

    private fun JSONObject.toWarning() = Warning(
        id = optLong("id", 0),
        employeeId = optString("employeeId", ""),
        employeeName = optString("employeeName", ""),
        level = optInt("level", 1),
        reason = optString("reason", ""),
        dateString = optString("dateString", "")
    )

    private fun Warning.toJSONObject() = JSONObject().apply {
        put("employeeId", employeeId)
        put("employeeName", employeeName)
        put("level", level)
        put("reason", reason)
        put("dateString", dateString)
    }

    private fun JSONObject.toWeeklyNdp() = WeeklyNdp(
        id = optLong("id", 0),
        employeeId = optString("employeeId", ""),
        weekNumber = optInt("weekNumber", 0),
        ndpScore = optInt("ndpScore", 0)
    )

    private fun WeeklyNdp.toJSONObject() = JSONObject().apply {
        put("employeeId", employeeId)
        put("weekNumber", weekNumber)
        put("ndpScore", ndpScore)
    }

    private fun JSONObject.toSalaryRecord() = SalaryRecord(
        id = optLong("id", 0),
        employeeId = optString("employeeId", ""),
        employeeName = optString("employeeName", ""),
        weekNumber = optInt("weekNumber", 0),
        amount = optDouble("amount", 0.0),
        status = optString("status", "Pending"),
        reasonOfRejection = optString("reasonOfRejection", ""),
        processedAt = optLong("processedAt", System.currentTimeMillis())
    )

    private fun SalaryRecord.toJSONObject() = JSONObject().apply {
        put("employeeId", employeeId)
        put("employeeName", employeeName)
        put("weekNumber", weekNumber)
        put("amount", amount)
        put("status", status)
        put("reasonOfRejection", reasonOfRejection)
        put("processedAt", processedAt)
    }

    private fun JSONObject.toBonusClaim() = BonusClaim(
        id = optLong("id", 0),
        employeeId = optString("employeeId", ""),
        employeeName = optString("employeeName", ""),
        platformLink = optString("platformLink", ""),
        screenshotInsights = optString("screenshotInsights", ""),
        viewCount = optLong("viewCount", 0),
        recommendedReward = optDouble("recommendedReward", 0.0),
        approvedReward = optDouble("approvedReward", 0.0),
        status = optString("status", "Pending Review"),
        reason = optString("reason", ""),
        dateString = optString("dateString", ""),
        claimType = optString("claimType", "fyp")
    )

    private fun BonusClaim.toJSONObject() = JSONObject().apply {
        put("employeeId", employeeId)
        put("employeeName", employeeName)
        put("platformLink", platformLink)
        put("screenshotInsights", screenshotInsights)
        put("viewCount", viewCount)
        put("recommendedReward", recommendedReward)
        put("approvedReward", approvedReward)
        put("status", status)
        put("reason", reason)
        put("dateString", dateString)
        put("claimType", claimType)
    }

    private fun JSONObject.toPayment() = Payment(
        id = optLong("id", 0),
        employeeId = optString("employeeId", ""),
        employeeName = optString("employeeName", ""),
        amount = optDouble("amount", 0.0),
        paymentType = optString("paymentType", ""),
        status = optString("status", ""),
        dateString = optString("dateString", "")
    )

    private fun Payment.toJSONObject() = JSONObject().apply {
        put("employeeId", employeeId)
        put("employeeName", employeeName)
        put("amount", amount)
        put("paymentType", paymentType)
        put("status", status)
        put("dateString", dateString)
    }

    private fun JSONObject.toAnnouncement() = Announcement(
        id = optLong("id", 0),
        title = optString("title", ""),
        content = optString("content", ""),
        isPinned = optBoolean("isPinned", false),
        createdAt = optLong("createdAt", System.currentTimeMillis())
    )

    private fun Announcement.toJSONObject() = JSONObject().apply {
        put("title", title)
        put("content", content)
        put("isPinned", isPinned)
        put("createdAt", createdAt)
    }

    private fun JSONObject.toSupportTicket() = SupportTicket(
        id = optLong("id", 0),
        employeeId = optString("employeeId", ""),
        employeeName = optString("employeeName", ""),
        title = optString("title", ""),
        description = optString("description", ""),
        reply = optString("reply", ""),
        status = optString("status", "Open"),
        createdAt = optLong("createdAt", System.currentTimeMillis())
    )

    private fun SupportTicket.toJSONObject() = JSONObject().apply {
        put("employeeId", employeeId)
        put("employeeName", employeeName)
        put("title", title)
        put("description", description)
        put("reply", reply)
        put("status", status)
        put("createdAt", createdAt)
    }
}
