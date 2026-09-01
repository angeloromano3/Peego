package com.example.peego.data.repository

import com.example.peego.data.local.BathroomDao
import com.example.peego.data.local.BathroomEntity
import com.example.peego.data.model.Bathroom
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository (padrão MVVM): é a única fonte de verdade para a ViewModel.
 * Estratégia offline-first: expõe sempre o Flow do Room; ao atualizar,
 * busca dados novos (aqui, do mock — troque por RetrofitInstance.api em produção)
 * e grava no Room, que por sua vez emite a lista atualizada automaticamente.
 */
class BathroomRepository(private val dao: BathroomDao) {

    val nearbyBathrooms: Flow<List<Bathroom>> =
        dao.observeAll().map { entities -> entities.map { it.toDomain() } }

    suspend fun refresh() {
        // Em produção: val remote = RetrofitInstance.api.getNearbyBathrooms(lat, lng)
        val remote = MockBathroomData.sample()
        val entities = remote.map {
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
        dao.insertAll(entities)
    }
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
