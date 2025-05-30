package com.example.todoapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.RetroFitClient
import com.example.todoapp.data.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TasksViewModel: ViewModel() {
//    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
//    val tasks: StateFlow<List<Task>> = _tasks

    var tasks by mutableStateOf<List<Task>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    private val _newTaskText = MutableStateFlow("")
    val newTaskText: StateFlow<String> = _newTaskText

    init {
        fetchTasks()
    }

    fun updateNewTaskText(newText: String) {
        _newTaskText.value = newText
    }

    fun addTask() {
        val currentText = _newTaskText.value
        if (currentText.isNotBlank()) {
            val newTask = Task(title = currentText)
            tasks = tasks + newTask
            _newTaskText.value = "" // مسح حقل الإدخال
        }
    }

    fun getItemById(id: Long): Task? {
        return tasks.find { it.id == id }
    }

    fun fetchTasks() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                tasks = RetroFitClient.apiService.getTasks()
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }

    fun addTask(task: Task, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val newTask = RetroFitClient.apiService.addTask(task)
                tasks = tasks + newTask
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "خطأ غير معروف")
            }
        }
    }
}