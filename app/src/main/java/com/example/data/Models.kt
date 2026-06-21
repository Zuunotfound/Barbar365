package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val fullName: String,
    val whatsappNumber: String,
    val passwordHash: String,
    val role: String, // "Admin", "Employee"
    val employeeId: String, // EMP-000001, etc.
    val status: String, // "Pending", "Active", "Rejected", "Suspended"
    val warningLevel: Int = 0, // 0, 1, 2, 3
    val registeredAt: Long = System.currentTimeMillis(),
    val telegramUsername: String = "",
    val bankName: String = "",
    val bankAccountNumber: String = "",
    val bankAccountName: String = "",
    val profilePictureUrl: String = "",
    val walletBalance: Double = 0.0,
    val ms001Username: String = "",
    val ms001ReferralLink: String = ""
)

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val contentCategory: String, // "Instagram", "TikTok", "Facebook", "YouTube Shorts"
    val videoUrl: String = "",
    val imageUrl: String = "",
    val fileUrl: String = "",
    val caption: String,
    val hashtags: String,
    val instructions: String,
    val deadline: Long,
    val isHidden: Boolean = false
)

@Entity(tableName = "task_submissions")
data class TaskSubmission(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val taskId: Long,
    val taskTitle: String,
    val employeeId: String,
    val employeeName: String,
    val platform: String, // Instagram, TikTok, Facebook, YouTube Shorts
    val postLink: String,
    val screenshotPath: String, // local path or mock indicator
    val submissionDate: Long = System.currentTimeMillis(),
    val status: String, // "Pending Review", "Approved", "Rejected"
    val rejectionReason: String = ""
)

@Entity(tableName = "daily_checkins")
data class DailyCheckIn(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val employeeId: String,
    val dateString: String, // "yyyy-MM-dd"
    val checkinTime: Long = System.currentTimeMillis()
)

@Entity(tableName = "warnings")
data class Warning(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val employeeId: String,
    val employeeName: String,
    val level: Int, // 1, 2, 3
    val reason: String,
    val dateString: String
)

@Entity(tableName = "weekly_ndp")
data class WeeklyNdp(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val employeeId: String,
    val weekNumber: Int,
    val ndpScore: Int
)

@Entity(tableName = "salary_records")
data class SalaryRecord(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val employeeId: String,
    val employeeName: String,
    val weekNumber: Int,
    val amount: Double,
    val status: String, // "Pending", "Approved", "Paid", "Rejected"
    val reasonOfRejection: String = "",
    val processedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "bonus_claims")
data class BonusClaim(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val employeeId: String,
    val employeeName: String,
    val platformLink: String,
    val screenshotInsights: String,
    val viewCount: Long,
    val recommendedReward: Double,
    val approvedReward: Double,
    val status: String, // "Pending Review", "Approved", "Rejected"
    val reason: String = "",
    val dateString: String = "",
    val claimType: String = "fyp" // "fyp", "ndp"
)

@Entity(tableName = "payments")
data class Payment(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val employeeId: String,
    val employeeName: String,
    val amount: Double,
    val paymentType: String, // "Weekly Salary", "FYP Bonus", "NDP Bonus", "Referral Reward", "Manual Bonus"
    val status: String, // "Pending", "Approved", "Paid", "Rejected"
    val dateString: String
)

@Entity(tableName = "announcements")
data class Announcement(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val isPinned: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "announcement_acks")
data class AnnouncementAck(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val announcementId: Long,
    val employeeId: String,
    val acknowledgedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "support_tickets")
data class SupportTicket(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val employeeId: String,
    val employeeName: String,
    val title: String,
    val description: String,
    val reply: String = "",
    val status: String = "Open", // "Open", "Replied"
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "activity_logs")
data class ActivityLog(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val employeeId: String,
    val action: String, // "Registration", "Login", "Task Submission", etc.
    val details: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "withdrawal_requests")
data class WithdrawalRequest(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val employeeId: String,
    val employeeName: String,
    val amount: Double,
    val status: String, // "Pending", "Approved", "Rejected"
    val bankName: String,
    val bankAccountNumber: String,
    val bankAccountName: String,
    val requestDate: Long = System.currentTimeMillis()
)
