package com.example.carscollectionsapp.presentation.main_screen.entities

sealed class MainScreenEvent {

    data object AddNewCarClicked : MainScreenEvent()

    data class OnCarClicked(val id: Long) : MainScreenEvent()

}