package com.example.carscollectionsapp.presentation.main_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carscollectionsapp.domain.CarsRepository
import com.example.carscollectionsapp.domain.entities.Car
import com.example.carscollectionsapp.presentation.main_screen.entities.MainScreenEffect
import com.example.carscollectionsapp.presentation.main_screen.entities.MainScreenEvent
import com.example.carscollectionsapp.presentation.main_screen.entities.MainScreenState
import com.example.carscollectionsapp.utils.listToString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val carsRepository: CarsRepository
) : ViewModel() {

    // todo аккуратно с ЖЦ флоу! Мб где-то использовать LD

    val searchQuery = MutableStateFlow("")

    private val _effect = MutableSharedFlow<MainScreenEffect>()
    val effect = _effect.asSharedFlow()

    private val _state = MutableStateFlow<MainScreenState>(MainScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            carsRepository.deleteAllCars()
            carsRepository.addCar(
                Car(
                    0,
                    "Aston Martin Vantage",
                    null,
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
                    null,
                    2012,
                    1.6F,
                    Calendar.getInstance().timeInMillis
                )
            )
            searchQuery.collectLatest { queryText ->
                load(queryText)
            }
        }
    }

    private suspend fun load(queryText: String) {
        try {
            _state.value = MainScreenState.Loading

            carsRepository.getCars(queryText).collectLatest { cars ->
                _state.value = MainScreenState.Successful(cars)
                Log.d("ABCD", cars.listToString())
            }
        } catch (e: Exception) {
            _state.value = MainScreenState.Error
        }
    }

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.AddNewCarClicked -> onAddNewCocktailClicked()

            is MainScreenEvent.OnCarClicked -> onCocktailClicked(event.id)
        }
    }

    private fun onAddNewCocktailClicked() {
        viewModelScope.launch {
            _effect.emit(MainScreenEffect.NavigateToCarAddScreen)
        }
    }

    private fun onCocktailClicked(id: Long) {
        viewModelScope.launch {
            _effect.emit(MainScreenEffect.NavigateToCarDetailsScreen(id))
        }
    }


}