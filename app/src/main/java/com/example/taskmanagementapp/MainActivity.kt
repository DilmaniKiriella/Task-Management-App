package com.example.taskmanagementapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanagementapp.database.TaskDatabase
import com.example.taskmanagementapp.repository.TaskRepository
import com.example.taskmanagementapp.viewmodel.TaskVieModelFactory
import com.example.taskmanagementapp.viewmodel.TaskViewModel

class MainActivity : AppCompatActivity() {

    lateinit var taskViewModel: TaskViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()

    }

    private fun setupViewModel(){
        val taskRepository = TaskRepository(TaskDatabase(this))
        val viewModelProviderFactory = TaskVieModelFactory(application, taskRepository)
        taskViewModel = ViewModelProvider(this, viewModelProviderFactory)[TaskViewModel::class.java]

    }

}