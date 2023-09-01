package com.example.carscollectionsapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carscollectionsapp.domain.CarsRepository
import com.example.carscollectionsapp.domain.SubscriptionsRepository
import com.example.carscollectionsapp.domain.entities.Car
import com.example.carscollectionsapp.presentation.settings.entities.SettingsScreenEffect
import com.example.carscollectionsapp.presentation.settings.entities.SettingsScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val subscriptionsRepository: SubscriptionsRepository,
    private val carsRepository: CarsRepository
) : ViewModel() {

    private val _effect = MutableSharedFlow<SettingsScreenEffect>()
    val effect = _effect.asSharedFlow()
    fun onEvent(event: SettingsScreenEvent) {
        when (event) {
            SettingsScreenEvent.OnResetClicked -> {
                onResetClicked()
            }

            SettingsScreenEvent.OnInitDbClicked -> {
                onInitDbClicked()
            }
        }
    }

    private fun onResetClicked() = viewModelScope.launch {
        subscriptionsRepository.reset()
        _effect.emit(SettingsScreenEffect.Reset)
    }

    private fun onInitDbClicked() = viewModelScope.launch {
        carsRepository.deleteAllCars()
        carsRepository.addCar(
            Car(
                0,
                "Aston Martin Vantage",
                "https://images.squarespace-cdn.com/content/v1/5caed8960cf57d49530e8c60/4431c223-545c-4f50-a0f4-4aea46504a7e/art-mg-astonmartinvantagev550g.jpg",
                1993,
                3.0F,
                Calendar.getInstance().timeInMillis
            )
        )
        carsRepository.addCar(
            Car(
                0,
                "Lada Vesta",
                null,
                2015,
                1.6F,
                Calendar.getInstance().timeInMillis
            )
        )
        carsRepository.addCar(
            Car(
                0,
                "Lada Granta",
                "https://foto.carexpert.ru/img/foto1680/vaz/vazgr038.jpg",
                2012,
                1.6F,
                Calendar.getInstance().timeInMillis
            )
        )

        _effect.emit(SettingsScreenEffect.DBInited)
    }

}