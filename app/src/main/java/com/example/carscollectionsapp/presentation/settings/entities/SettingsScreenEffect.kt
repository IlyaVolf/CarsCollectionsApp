package com.example.carscollectionsapp.presentation.settings.entities

sealed class SettingsScreenEffect {

    data object Reset: SettingsScreenEffect()

    data object DBInited: SettingsScreenEffect()

}