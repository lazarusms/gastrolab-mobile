package com.example.gastrolab

data class Reservation(
    val customerName: String,
    val numberOfPeople: Int,
    val date: String,
    val time: String,
    val contactInfo: String,
    val status: String,
)