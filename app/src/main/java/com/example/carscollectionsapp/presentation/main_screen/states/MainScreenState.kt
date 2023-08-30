package com.example.carscollectionsapp.presentation.main_screen.states

import com.example.carscollectionsapp.domain.entities.Car

sealed class MainScreenState {

    data class Successful(val cars: List<Car>) : MainScreenState()

    data object Error : MainScreenState()

    data object Loading : MainScreenState()

}