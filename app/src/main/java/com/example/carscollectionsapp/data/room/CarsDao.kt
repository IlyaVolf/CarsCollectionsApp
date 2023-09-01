package com.example.carscollectionsapp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.carscollectionsapp.data.room.entities.CarDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarsDao {

    @Query("SELECT * FROM cars WHERE :id = id")
    fun getById(id: Long): Flow<CarDbEntity>

    @Query("SELECT * FROM cars WHERE name LIKE '%' || :searchQuery || '%'")
    fun getCars(searchQuery: String): Flow<List<CarDbEntity>>

    @Insert
    suspend fun addCar(carDbEntity: CarDbEntity)

    @Update
    suspend fun updateCar(carDbEntity: CarDbEntity)

    @Delete
    suspend fun deleteCar(carDbEntity: CarDbEntity)

    @Query("DELETE FROM cars")
    suspend fun deleteAllCars()

}