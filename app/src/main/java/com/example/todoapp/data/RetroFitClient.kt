package com.example.todoapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}