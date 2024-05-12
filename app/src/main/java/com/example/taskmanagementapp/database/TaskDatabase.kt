package com.example.taskmanagementapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskmanagementapp.model.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {

    abstract fun getTaskDao(): TaskDAO

    companion object{
        @Volatile   //ensures the changes are made quickly
        private var instance: TaskDatabase? = null //variable to store data
        private var LOCK = Any() //ensures only one instance created

        operator fun invoke(context: Context) = instance?:
        synchronized(LOCK){
            instance ?:
            createDatabase(context).also{
                instance = it
            }
        }

        //responsible for creating database
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java,
                "task_db"
            ).build()

    }
}