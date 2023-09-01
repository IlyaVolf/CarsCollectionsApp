package com.example.carscollectionsapp.presentation.car_add_screen.entities

sealed class CarAddScreenState {

    data class Default(val carAddContainer: CarAddContainer) : CarAddScreenState()

    data object Saving : CarAddScreenState()

    data class Error(val error: Throwable) : CarAddScreenState()

}