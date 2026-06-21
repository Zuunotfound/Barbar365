package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ZuuDao {
    // --- User Queries ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM users WHERE whatsappNumber = :wa LIMIT 1")
    suspend fun getUserByWhatsApp(wa: String): User?

    @Query("SELECT * FROM users WHERE employeeId = :empId LIMIT 1")
    suspend fun getUserByEmployeeId(empId: String): User?

    @Query("SELECT * FROM users ORDER BY registeredAt DESC")
    fun getAllUsersFlow(): Flow<List<User>>

    @Query("SELECT * FROM users ORDER BY registeredAt DESC")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int

    @Delete
    suspend fun deleteUser(user: User)

    // --- Task Queries ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks ORDER BY deadline ASC")
    fun getAllTasksFlow(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE isHidden = 0 ORDER BY deadline ASC")
    fun getActiveTasksFlow(): Flow<List<Task>>

    // --- Task Submission Queries ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubmission(submission: TaskSubmission)

    @Update
    suspend fun updateSubmission(submission: TaskSubmission)

    @Query("SELECT * FROM task_submissions ORDER BY submissionDate DESC")
    fun getAllSubmissionsFlow(): Flow<List<TaskSubmission>>

    @Query("SELECT * FROM task_submissions WHERE employeeId = :empId ORDER BY submissionDate DESC")
    fun getSubmissionsForEmployeeFlow(empId: String): Flow<List<TaskSubmission>>

    // --- Daily Check-In Queries ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCheckIn(checkIn: DailyCheckIn)

    @Query("SELECT * FROM daily_checkins ORDER BY checkinTime DESC")
    fun getAllCheckInsFlow(): Flow<List<DailyCheckIn>>

    @Query("SELECT * FROM daily_checkins WHERE employeeId = :empId ORDER BY checkinTime DESC")
    fun getCheckInsForEmployeeFlow(empId: String): Flow<List<DailyCheckIn>>

    // --- Warning Queries ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWarning(warning: Warning)

    @Query("SELECT * FROM warnings ORDER BY dateString DESC")
    fun getAllWarningsFlow(): Flow<List<Warning>>

    @Query("SELECT * FROM warnings WHERE employeeId = :empId ORDER BY dateString DESC")
    fun getWarningsForEmployeeFlow(empId: String): Flow<List<Warning>>

    // --- NDP Queries ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNdp(ndp: WeeklyNdp)

    @Query("SELECT * FROM weekly_ndp ORDER BY weekNumber DESC")
    fun getAllNdpFlow(): Flow<List<WeeklyNdp>>

    @Query("SELECT * FROM weekly_ndp WHERE employeeId = :empId ORDER BY weekNumber DESC")
    fun getNdpForEmployeeFlow(empId: String): Flow<List<WeeklyNdp>>

    // --- Salary Queries ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSalary(salary: SalaryRecord)

    @Update
    suspend fun updateSalary(salary: SalaryRecord)

    @Query("SELECT * FROM salary_records ORDER BY weekNumber DESC")
    fun getAllSalariesFlow(): Flow<List<SalaryRecord>>

    @Query("SELECT * FROM salary_records WHERE employeeId = :empId ORDER BY weekNumber DESC")
    fun getSalariesForEmployeeFlow(empId: String): Flow<List<SalaryRecord>>

    // --- Bonus Claims Queries ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBonusClaim(claim: BonusClaim)

    @Update
    suspend fun updateBonusClaim(claim: BonusClaim)

    @Query("SELECT * FROM bonus_claims ORDER BY dateString DESC")
    fun getAllBonusClaimsFlow(): Flow<List<BonusClaim>>

    @Query("SELECT * FROM bonus_claims WHERE employeeId = :empId ORDER BY dateString DESC")
    fun getBonusClaimsForEmployeeFlow(empId: String): Flow<List<BonusClaim>>

    // --- Payment Queries ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: Payment)

    @Update
    suspend fun updatePayment(payment: Payment)

    @Query("SELECT * FROM payments ORDER BY dateString DESC")
    fun getAllPaymentsFlow(): Flow<List<Payment>>

    @Query("SELECT * FROM payments WHERE employeeId = :empId ORDER BY dateString DESC")
    fun getPaymentsForEmployeeFlow(empId: String): Flow<List<Payment>>

    // --- Announcement Queries ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnnouncement(announcement: Announcement)

    @Update
    suspend fun updateAnnouncement(announcement: Announcement)

    @Delete
    suspend fun deleteAnnouncement(announcement: Announcement)

    @Query("SELECT * FROM announcements ORDER BY isPinned DESC, createdAt DESC")
    fun getAllAnnouncementsFlow(): Flow<List<Announcement>>

    // --- Announcement Acknowledgement Queries ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAck(ack: AnnouncementAck)

    @Query("SELECT * FROM announcement_acks WHERE employeeId = :empId")
    suspend fun getAcksForEmployee(empId: String): List<AnnouncementAck>

    // --- Support Ticket Queries ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: SupportTicket)

    @Update
    suspend fun updateTicket(ticket: SupportTicket)

    @Query("SELECT * FROM support_tickets ORDER BY createdAt DESC")
    fun getAllTicketsFlow(): Flow<List<SupportTicket>>

    @Query("SELECT * FROM support_tickets WHERE employeeId = :empId ORDER BY createdAt DESC")
    fun getTicketsForEmployeeFlow(empId: String): Flow<List<SupportTicket>>

    // --- Activity Log Queries ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: ActivityLog)

    @Query("SELECT * FROM activity_logs ORDER BY timestamp DESC")
    fun getAllLogsFlow(): Flow<List<ActivityLog>>

    // --- Database Purging / Local Data Cleaning ---
    @Query("DELETE FROM users")
    suspend fun clearUsersTable()

    @Query("DELETE FROM tasks")
    suspend fun clearTasksTable()

    @Query("DELETE FROM task_submissions")
    suspend fun clearSubmissionsTable()

    @Query("DELETE FROM daily_checkins")
    suspend fun clearCheckinsTable()

    @Query("DELETE FROM warnings")
    suspend fun clearWarningsTable()

    @Query("DELETE FROM weekly_ndp")
    suspend fun clearNdpTable()

    @Query("DELETE FROM salary_records")
    suspend fun clearSalariesTable()

    @Query("DELETE FROM bonus_claims")
    suspend fun clearClaimsTable()

    @Query("DELETE FROM payments")
    suspend fun clearPaymentsTable()

    @Query("DELETE FROM announcements")
    suspend fun clearAnnouncementsTable()

    @Query("DELETE FROM announcement_acks")
    suspend fun clearAcksTable()

    @Query("DELETE FROM support_tickets")
    suspend fun clearTicketsTable()

    @Query("DELETE FROM activity_logs")
    suspend fun clearLogsTable()

    // --- Withdrawal Request Queries ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWithdrawalRequest(req: WithdrawalRequest)

    @Update
    suspend fun updateWithdrawalRequest(req: WithdrawalRequest)

    @Query("SELECT * FROM withdrawal_requests ORDER BY requestDate DESC")
    fun getAllWithdrawalRequestsFlow(): Flow<List<WithdrawalRequest>>

    @Query("SELECT * FROM withdrawal_requests WHERE employeeId = :empId ORDER BY requestDate DESC")
    fun getWithdrawalRequestsForEmployeeFlow(empId: String): Flow<List<WithdrawalRequest>>

    @Query("DELETE FROM withdrawal_requests")
    suspend fun clearWithdrawalsTable()
}
