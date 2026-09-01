package com.example.peego

import com.example.peego.data.model.Bathroom
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * sem depender de Android (por isso fica em src/test, não em androidTest).
 */
class ExploreViewModelTest {

    @Test
    fun `lista de banheiros deve ser ordenada por distancia`() {
        val bathrooms = listOf(
            sample(id = "1", distance = 500),
            sample(id = "2", distance = 150),
            sample(id = "3", distance = 300)
        )

        val sorted = bathrooms.sortedBy { it.distanceMeters }

        assertEquals("2", sorted.first().id)
        assertEquals("1", sorted.last().id)
    }

    private fun sample(id: String, distance: Int) = Bathroom(
        id = id,
        name = "Banheiro $id",
        distanceMeters = distance,
        rating = 4.0,
        isOpenNow = true,
        isAccessible = true,
        isFree = true,
        imageUrl = "",
        latitude = 0.0,
        longitude = 0.0
    )
}
