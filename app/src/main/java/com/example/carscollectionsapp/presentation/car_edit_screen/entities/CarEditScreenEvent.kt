package com.example.carscollectionsapp.presentation.car_edit_screen.entities

sealed class CarEditScreenEvent {

    data class OnEnterScreen(val carId: Long) : CarEditScreenEvent()

    data class OnNameChanged(val newNameString: String) : CarEditScreenEvent()

    data class OnPhotoChanged(val newPhotoUriString: String?) : CarEditScreenEvent()

    data class OnYearChanged(val newYearString: String) : CarEditScreenEvent()

    data class OnEngineCapacityChanged(val newEngineCapacityString: String) : CarEditScreenEvent()

    data object OnSaveClicked : CarEditScreenEvent()

    data object OnDeleteClicked : CarEditScreenEvent()

    data object OnCancelClicked : CarEditScreenEvent()

    data object OnCancelConfirmClicked : CarEditScreenEvent()

    data object OnDeleteConfirmClicked : CarEditScreenEvent()

}