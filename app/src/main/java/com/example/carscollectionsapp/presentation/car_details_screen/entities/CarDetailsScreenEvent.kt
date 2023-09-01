package com.example.carscollectionsapp.presentation.car_details_screen.entities

sealed class CarDetailsScreenEvent {

    data object OnEditClicked : CarDetailsScreenEvent()

    data class OnEnterScreen(val carId: Long) : CarDetailsScreenEvent()

}