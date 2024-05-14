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
        @Volatile   //ensures the changes are made quickly(changes made in one thread appear to other thread instantly)
        private var instance: TaskDatabase? = null //Hold the singleton instance of the taskdatabase
        private var LOCK = Any() //ensures only one instance created(Synchronize)

        operator fun invoke(context: Context) = instance?://for the companion obj,allows to create instance of taskdb
        synchronized(LOCK){//instance? check if instance is already initialized,if not enters to synchronized block using lock obj
            instance ?:
            createDatabase(context).also{
                instance = it
            }
        }

        //responsible for creating database instance
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java,
                "task_db"
            ).build()

    }
}