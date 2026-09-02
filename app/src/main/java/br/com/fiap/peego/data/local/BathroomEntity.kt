package br.com.fiap.peego.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

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
