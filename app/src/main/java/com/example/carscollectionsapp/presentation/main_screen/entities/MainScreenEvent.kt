package com.example.carscollectionsapp.presentation.main_screen.entities

sealed class MainScreenEvent {

    data object OnAddNewCarClicked : MainScreenEvent()

    data object OnSettingsClicked : MainScreenEvent()

    data class OnCarClicked(val id: Long) : MainScreenEvent()

}