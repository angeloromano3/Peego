package br.com.fiap.peego.model

data class Bathroom(
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
