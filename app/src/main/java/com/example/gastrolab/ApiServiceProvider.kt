package com.example.gastrolab

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServiceProvider {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/") // Use este endereço IP para se conectar ao localhost do emulador
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}