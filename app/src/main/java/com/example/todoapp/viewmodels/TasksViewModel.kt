package com.example.todoapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.todoapp.data.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TasksViewModel : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    private val _newTaskText = MutableStateFlow("")
    val newTaskText: StateFlow<String> = _newTaskText

    fun updateNewTaskText(newText: String) {
        _newTaskText.value = newText
    }

    fun addTask() {
        val currentText = _newTaskText.value
        if (currentText.isNotBlank()) {
            val newTask = Task(title = currentText)
            _tasks.value = _tasks.value + newTask
            _newTaskText.value = "" // مسح حقل الإدخال
        }
    }

    fun getItemById(id: Long): Task? {
        return _tasks.value.find { it.id == id }
    }
}