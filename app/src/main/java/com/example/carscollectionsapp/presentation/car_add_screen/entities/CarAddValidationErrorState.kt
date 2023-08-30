package com.example.carscollectionsapp.presentation.car_add_screen.entities

data class CarAddValidationErrorState(
    val isNameMissing: Boolean,
    val isYearMissing: Boolean,
)