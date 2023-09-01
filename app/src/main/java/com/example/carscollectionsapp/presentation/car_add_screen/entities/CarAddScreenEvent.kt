package com.example.carscollectionsapp.presentation.car_add_screen.entities

sealed class CarAddScreenEvent {

    data class OnNameChanged(val newNameString: String) : CarAddScreenEvent()

    data class OnPhotoChanged(val newPhotoUriString: String?) : CarAddScreenEvent()

    data class OnYearChanged(val newYearString: String) : CarAddScreenEvent()

    data class OnEngineCapacityChanged(val newEngineCapacityString: String) : CarAddScreenEvent()

    data object OnCancelClicked : CarAddScreenEvent()

    data object OnCancelConfirmClicked : CarAddScreenEvent()

    data object OnSaveClicked : CarAddScreenEvent()

}