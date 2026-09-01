package com.example.peego.data.repository

import com.example.peego.data.remote.BathroomDto

/**
 * Dados de exemplo, usados enquanto o back-end real (Retrofit + H2 Database)
 * não está disponível. Em produção, troque MockBathroomData.sample() pela
 * chamada real: RetrofitInstance.api.getNearbyBathrooms(lat, lng).
 */
object MockBathroomData {
    fun sample(): List<BathroomDto> = listOf(
        BathroomDto(
            id = "1",
            name = "McDonald's",
            distanceMeters = 150,
            rating = 4.5,
            isOpenNow = true,
            isAccessible = true,
            isFree = true,
            imageUrl = "https://images.unsplash.com/photo-1552566626-52f8b828add9?w=300",
            latitude = -23.5980,
            longitude = -46.6870
        ),
        BathroomDto(
            id = "2",
            name = "Shopping Vila Olímpia",
            distanceMeters = 420,
            rating = 4.2,
            isOpenNow = true,
            isAccessible = true,
            isFree = true,
            imageUrl = "https://images.unsplash.com/photo-1519567770579-c2fc5436e08d?w=300",
            latitude = -23.5950,
            longitude = -46.6840
        ),
        BathroomDto(
            id = "3",
            name = "Posto Ipiranga",
            distanceMeters = 680,
            rating = 3.9,
            isOpenNow = false,
            isAccessible = false,
            isFree = false,
            imageUrl = "https://images.unsplash.com/photo-1545262810-77515befe149?w=300",
            latitude = -23.6010,
            longitude = -46.6905
        )
    )
}
