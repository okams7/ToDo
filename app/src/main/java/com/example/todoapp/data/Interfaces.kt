package com.example.todoapp.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("tasks") // مثال: https://yourapi.com/tasks
    suspend fun getTasks(): List<Task>

    @POST("tasks")
    suspend fun addTask(@Body task: Task): Task
}