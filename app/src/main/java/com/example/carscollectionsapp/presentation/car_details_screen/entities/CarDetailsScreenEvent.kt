package com.example.carscollectionsapp.presentation.car_details_screen.entities

import com.example.carscollectionsapp.domain.entities.Car

sealed class CarDetailsScreenEvent {

    data object OnEditClicked : CarDetailsScreenEvent()

    data class OnEnterScreen(val carId: Long) : CarDetailsScreenEvent()

}