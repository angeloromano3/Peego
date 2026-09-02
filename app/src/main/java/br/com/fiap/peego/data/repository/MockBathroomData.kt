package br.com.fiap.peego.data.repository

import br.com.fiap.peego.model.Bathroom

object MockBathroomData {
    val bathrooms = listOf(
        Bathroom(
            id = "1",
            name = "McDonald's",
            distanceMeters = 150,
            rating = 4.5,
            isOpenNow = true,
            isAccessible = true,
            isFree = true,
            imageUrl = "https://lh5.googleusercontent.com/p/AF1QipN...",
            latitude = -23.5945,
            longitude = -46.6870
        ),
        Bathroom(
            id = "2",
            name = "Shopping Eldorado",
            distanceMeters = 320,
            rating = 4.2,
            isOpenNow = true,
            isAccessible = true,
            isFree = true,
            imageUrl = "https://lh5.googleusercontent.com/p/AF1QipN...",
            latitude = -23.5730,
            longitude = -46.6950
        ),
        Bathroom(
            id = "3",
            name = "Posto Shell Faria Lima",
            distanceMeters = 480,
            rating = 3.8,
            isOpenNow = false,
            isAccessible = false,
            isFree = false,
            imageUrl = "",
            latitude = -23.5870,
            longitude = -46.6780
        )
    )
}
