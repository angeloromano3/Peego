package br.com.fiap.peego.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BathroomDao {
    @Query("SELECT * FROM bathrooms ORDER BY distanceMeters ASC")
    fun observeAll(): Flow<List<BathroomEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<BathroomEntity>)

    @Query("DELETE FROM bathrooms")
    suspend fun clear()
}
