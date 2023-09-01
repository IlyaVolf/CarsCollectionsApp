package com.example.carscollectionsapp.presentation.car_details_screen.entities

sealed class CarDetailsScreenEffect {

    data class NavigateToCarEditScreen(val carId: Long) : CarDetailsScreenEffect()

}