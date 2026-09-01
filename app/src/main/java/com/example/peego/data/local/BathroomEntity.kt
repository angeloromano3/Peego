package com.example.peego.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Entidade Room: cache local dos banheiros (SQLite por baixo dos panos). */
@Entity(tableName = "bathrooms")
data class BathroomEntity(
    @PrimaryKey val id: String,
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
