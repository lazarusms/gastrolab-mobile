package com.example.gastrolab

import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call


interface ApiService {
    @POST("reservation/register")
    fun registerReservation(@Body reservation: Reservation): Call<ResponseBody>
}