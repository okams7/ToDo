package com.example.todoapp.data

data class Task(
//    val id: Long = System.currentTimeMillis(), //used for demo purposes
    val id: Int? = null,
    val title: String,
    val description: String? = null,
    val isCompleted: Boolean = false
)
