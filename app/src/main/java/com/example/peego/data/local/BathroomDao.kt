package com.example.peego.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BathroomDao {

    // Kotlin Flow: qualquer alteração na tabela emite automaticamente uma nova lista.
    @Query("SELECT * FROM bathrooms ORDER BY distanceMeters ASC")
    fun observeAll(): Flow<List<BathroomEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<BathroomEntity>)

    @Query("DELETE FROM bathrooms")
    suspend fun clear()
}
