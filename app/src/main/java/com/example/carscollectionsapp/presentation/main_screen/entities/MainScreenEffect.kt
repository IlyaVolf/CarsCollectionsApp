package com.example.carscollectionsapp.presentation.main_screen.entities

sealed class MainScreenEffect {

    data object NavigateToCarAddScreen : MainScreenEffect()

    data class NavigateToCarDetailsScreen(val carId: Long) : MainScreenEffect()

    data object NavigateToSettingsScreen : MainScreenEffect()

}