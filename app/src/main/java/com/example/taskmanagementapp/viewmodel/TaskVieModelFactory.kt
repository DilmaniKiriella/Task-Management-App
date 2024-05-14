package com.example.taskmanagementapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanagementapp.repository.TaskRepository

class TaskVieModelFactory(val app: Application, private val taskRepository: TaskRepository) : ViewModelProvider.Factory {//class that instantiate and return view model

    override fun <T : ViewModel> create(modelClass: Class<T>): T {//override the create method of view model provider factory interface
        return TaskViewModel(app, taskRepository) as T
    }
}