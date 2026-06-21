package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        User::class,
        Task::class,
        TaskSubmission::class,
        DailyCheckIn::class,
        Warning::class,
        WeeklyNdp::class,
        SalaryRecord::class,
        BonusClaim::class,
        Payment::class,
        Announcement::class,
        AnnouncementAck::class,
        SupportTicket::class,
        ActivityLog::class,
        WithdrawalRequest::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val dao: ZuuDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "zuu_work_hub_db"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
