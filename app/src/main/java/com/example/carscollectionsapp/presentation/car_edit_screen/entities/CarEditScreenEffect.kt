package com.example.carscollectionsapp.presentation.car_edit_screen.entities

sealed class CarEditScreenEffect {

    data object NavigateBackOnSuccessAdding : CarEditScreenEffect()

    data object NavigateBackOnSuccessDeleting : CarEditScreenEffect()

    data object OpenCancelDialog : CarEditScreenEffect()

    data object OpenDeleteDialog : CarEditScreenEffect()

    data object NavigateBack : CarEditScreenEffect()

}