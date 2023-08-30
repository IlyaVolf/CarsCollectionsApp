package com.example.carscollectionsapp.presentation.car_add_screen.entities

import com.example.carscollectionsapp.domain.entities.Car

sealed class CarAddScreenState {

    data object Loading : CarAddScreenState()

    data class Successful(val cars: Car?) : CarAddScreenState()

    data object Saving : CarAddScreenState()

    data object SaveSuccessful : CarAddScreenState()

    data class Error(val error: Throwable) : CarAddScreenState()

}