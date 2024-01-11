package com.aswindev.actionable.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class ActionAbleDatabase : RoomDatabase() {

    companion object {
        @Volatile // important property that puts in main memory and not in any thread
        private var DB_INSTANCE: ActionAbleDatabase? = null

        fun getDatabase(context: Context): ActionAbleDatabase {
            return DB_INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    ActionAbleDatabase::class.java,
                    "actionable-database"
                ).build()
                DB_INSTANCE = instance
                instance
            }
        }
    }

    abstract fun getTaskDao() : TaskDao
}


