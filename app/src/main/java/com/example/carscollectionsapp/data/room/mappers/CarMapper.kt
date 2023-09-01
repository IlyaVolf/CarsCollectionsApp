package com.example.carscollectionsapp.data.room.mappers

import com.example.carscollectionsapp.data.room.entities.CarDbEntity
import com.example.carscollectionsapp.domain.entities.Car
import javax.inject.Inject

class CarMapper @Inject constructor() {

    fun toCar(carDbEntity: CarDbEntity): Car = Car(
        id = carDbEntity.id,
        name = carDbEntity.name,
        photo = carDbEntity.photo,
        year = carDbEntity.year,
        engineCapacity = carDbEntity.engineCapacity,
        dateAdded = carDbEntity.dateAdded
    )

    fun toCarList(carDbEntityList: List<CarDbEntity>): List<Car> =
        carDbEntityList.map { carDbEntity ->
            Car(
                id = carDbEntity.id,
                name = carDbEntity.name,
                photo = carDbEntity.photo,
                year = carDbEntity.year,
                engineCapacity = carDbEntity.engineCapacity,
                dateAdded = carDbEntity.dateAdded
            )
        }

    fun toCarDbEntity(car: Car): CarDbEntity = CarDbEntity(
        id = car.id,
        name = car.name,
        photo = car.photo,
        year = car.year,
        engineCapacity = car.engineCapacity,
        dateAdded = car.dateAdded
    )

}