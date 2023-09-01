package com.example.carscollectionsapp.presentation.settings.entities

sealed class SettingsScreenEvent {

    data object OnResetClicked : SettingsScreenEvent()

    data object OnInitDbClicked: SettingsScreenEvent()

}