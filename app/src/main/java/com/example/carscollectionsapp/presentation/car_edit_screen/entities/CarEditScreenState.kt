package com.example.carscollectionsapp.presentation.car_edit_screen.entities

sealed class CarEditScreenState {

    data class Default(val carAddContainer: CarEditContainer) : CarEditScreenState()

    data object Saving : CarEditScreenState()

    data class Error(val error: Throwable) : CarEditScreenState()

}