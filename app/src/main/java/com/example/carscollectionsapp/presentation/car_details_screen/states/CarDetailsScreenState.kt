package com.example.carscollectionsapp.presentation.car_details_screen.states

import com.example.carscollectionsapp.domain.entities.Car

sealed class CarDetailsScreenState {

    data class Successful(val cars: Car) : CarDetailsScreenState()

    data object Error : CarDetailsScreenState()

    data object Loading : CarDetailsScreenState()

}