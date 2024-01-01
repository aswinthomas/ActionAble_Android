package com.aswindev.actionable.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class ActionAbleDatabase : RoomDatabase() {
    companion object {
        fun createDatabase(context: Context): ActionAbleDatabase {
            return Room.databaseBuilder(
                context,
                ActionAbleDatabase::class.java,
                "actionable-database"
            ).build()
        }
    }

    abstract fun getTaskDao() : TaskDao
}


