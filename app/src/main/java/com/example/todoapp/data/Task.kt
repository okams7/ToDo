package com.example.todoapp.data

data class Task(
    val id: Long = System.currentTimeMillis(),
    val title: String,
    val description: String? = null,
    val isCompleted: Boolean = false
)
