package com.example.carscollectionsapp.presentation.car_details_screen.entities

import com.example.carscollectionsapp.domain.entities.Car

sealed class CarDetailsScreenState {

    data class Successful(val car: Car) : CarDetailsScreenState()

    data object Error : CarDetailsScreenState()

    data object Loading : CarDetailsScreenState()

}