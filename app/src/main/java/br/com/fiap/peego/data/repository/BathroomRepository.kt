package br.com.fiap.peego.data.repository

import br.com.fiap.peego.data.local.BathroomDao
import br.com.fiap.peego.data.local.BathroomEntity
import br.com.fiap.peego.model.Bathroom
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BathroomRepository(private val dao: BathroomDao) {

    val nearbyBathrooms: Flow<List<Bathroom>> = dao.observeAll().map { list ->
        list.map { it.toDomain() }
    }

    suspend fun refresh() {
        val mock = MockBathroomData.bathrooms.map {
            BathroomEntity(
                id = it.id,
                name = it.name,
                distanceMeters = it.distanceMeters,
                rating = it.rating,
                isOpenNow = it.isOpenNow,
                isAccessible = it.isAccessible,
                isFree = it.isFree,
                imageUrl = it.imageUrl,
                latitude = it.latitude,
                longitude = it.longitude
            )
        }
        dao.clear()
        dao.insertAll(mock)
    }

    private fun BathroomEntity.toDomain() = Bathroom(
        id = id,
        name = name,
        distanceMeters = distanceMeters,
        rating = rating,
        isOpenNow = isOpenNow,
        isAccessible = isAccessible,
        isFree = isFree,
        imageUrl = imageUrl,
        latitude = latitude,
        longitude = longitude
    )
}
