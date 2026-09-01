package com.example.peego.data.remote

/** DTO retornado pela API (Retrofit + Gson fazem a desserialização do JSON). */
data class BathroomDto(
    val id: String,
    val name: String,
    val distanceMeters: Int,
    val rating: Double,
    val isOpenNow: Boolean,
    val isAccessible: Boolean,
    val isFree: Boolean,
    val imageUrl: String,
    val latitude: Double,
    val longitude: Double
)
