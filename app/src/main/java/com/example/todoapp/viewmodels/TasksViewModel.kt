package com.example.todoapp.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TasksViewModel: ViewModel() {
    private val _tasks = MutableStateFlow<List<String>>(emptyList())
    val tasks: StateFlow<List<String>> = _tasks

    private val _newTaskText = MutableStateFlow("")
    val newTaskText: StateFlow<String> = _newTaskText

    fun updateNewTaskText(newText: String) {
        _newTaskText.value = newText
    }

    fun addTask() {
        val currentText = _newTaskText.value
        if (currentText.isNotBlank()) {
            _tasks.value = _tasks.value + currentText
            _newTaskText.value = "" // مسح حقل الإدخال
        }
    }
}