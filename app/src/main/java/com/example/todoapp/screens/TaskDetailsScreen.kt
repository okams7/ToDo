package com.example.todoapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.todoapp.viewmodels.TasksViewModel

@Composable
fun TaskDetailsScreen(vm: TasksViewModel, taskId: Long) {
    val task = vm.getItemById(taskId)
    if (task != null) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text("Task Details Screen: $taskId")
            Text("Title: ${task.title}")
            Text("Description: ${task.description ?: "No description"}")
        }
    } else {
        Text("Task not found")
    }
}