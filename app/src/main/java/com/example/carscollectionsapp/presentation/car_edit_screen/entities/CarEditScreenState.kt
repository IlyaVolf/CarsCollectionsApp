package com.example.carscollectionsapp.presentation.car_edit_screen.entities

sealed class CarEditScreenState {

    data object Loading : CarEditScreenState()

    data class Successful(val carAddContainer: CarEditContainer) : CarEditScreenState()

    data object Saving : CarEditScreenState()

    data class Error(val error: Throwable) : CarEditScreenState()

}