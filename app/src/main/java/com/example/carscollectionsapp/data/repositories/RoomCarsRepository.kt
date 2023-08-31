package com.example.carscollectionsapp.data.repositories

import com.example.carscollectionsapp.data.room.CarsDao
import com.example.carscollectionsapp.data.room.mappers.CarMapper
import com.example.carscollectionsapp.di.IoDispatcher
import com.example.carscollectionsapp.domain.CarsRepository
import com.example.carscollectionsapp.domain.entities.Car
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomCarsRepository @Inject constructor(
    private val carsDao: CarsDao,
    private val carsMapper: CarMapper,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : CarsRepository {

    override fun getCars(searchQuery: String): Flow<List<Car>> {
        return carsDao.getCars(searchQuery).map { carsMapper.toCarList(it) }.flowOn(dispatcher)
    }

    override fun getById(id: Long): Flow<Car> {
        return carsDao.getById(id).map { carsMapper.toCar(it) }.flowOn(dispatcher)
    }

    override suspend fun addCar(car: Car) = withContext(dispatcher) {
        val carDbEntity = carsMapper.toCarDbEntity(car)
        carsDao.addCar(carDbEntity)
    }

    override suspend fun updateCar(car: Car) = withContext(dispatcher) {
        val carDbEntity = carsMapper.toCarDbEntity(car)
        carsDao.updateCar(carDbEntity)
    }

    override suspend fun deleteCar(car: Car) = withContext(dispatcher) {
        val carDbEntity = carsMapper.toCarDbEntity(car)
        carsDao.deleteCar(carDbEntity)
    }

    override suspend fun deleteAllCars() = withContext(dispatcher) {
        carsDao.deleteAllCars()
    }


}