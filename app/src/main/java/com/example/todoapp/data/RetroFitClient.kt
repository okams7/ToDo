package com.example.todoapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val localHostAndroid = "10.0.2.2"
//34.201.252.66
const val awsPublicIPApi = "34.201.252.66"
//const val BASE_URL = "http://10.0.2.2:3000/api/"
const val BASE_URL = "http://$awsPublicIPApi:3000/api/"


object RetroFitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}