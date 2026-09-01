package com.example.peego.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Contrato da API REST. Aponte RetrofitInstance.BASE_URL para o back-end
 * (por exemplo, o servidor de testes com H2 Database fornecido na disciplina)
 * quando ele estiver disponível.
 */
interface BathroomApiService {
    @GET("bathrooms/nearby")
    suspend fun getNearbyBathrooms(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("radius") radiusMeters: Int = 1500
    ): List<BathroomDto>
}
