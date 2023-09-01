package com.example.carscollectionsapp.presentation.car_add_screen.entities

sealed class CarAddScreenEffect {

    data object NavigateBack : CarAddScreenEffect()

    data object OpenDialog : CarAddScreenEffect()

    data object NavigateBackOnSuccessAdding : CarAddScreenEffect()

}