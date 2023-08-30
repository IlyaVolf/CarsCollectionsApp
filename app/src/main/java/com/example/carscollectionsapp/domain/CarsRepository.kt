package com.example.carscollectionsapp.domain

import com.example.carscollectionsapp.domain.entities.Car
import kotlinx.coroutines.flow.Flow

interface CarsRepository {

    fun getCars(searchQuery: String): Flow<List<Car>>

    fun getById(id: Long): Flow<Car>

    suspend fun addCar(car: Car)

    suspend fun updateCar(car: Car)

    suspend fun deleteCar(car: Car)

    suspend fun  deleteAllCars()

}