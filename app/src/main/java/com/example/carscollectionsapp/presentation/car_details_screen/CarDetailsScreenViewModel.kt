package com.example.carscollectionsapp.presentation.car_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carscollectionsapp.domain.CarsRepository
import com.example.carscollectionsapp.presentation.car_details_screen.entities.CarDetailsScreenEffect
import com.example.carscollectionsapp.presentation.car_details_screen.entities.CarDetailsScreenEvent
import com.example.carscollectionsapp.presentation.car_details_screen.entities.CarDetailsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarDetailsScreenViewModel @Inject constructor(
    private val carsRepository: CarsRepository
) : ViewModel() {

    private val _effect = MutableSharedFlow<CarDetailsScreenEffect>()
    val effect = _effect.asSharedFlow()

    private val _state = MutableStateFlow<CarDetailsScreenState>(CarDetailsScreenState.Loading)
    val state = _state.asStateFlow()

    fun onEvent(event: CarDetailsScreenEvent) {
        when (event) {
            is CarDetailsScreenEvent.OnEnterScreen -> onEnterScreen(event.carId)

            is CarDetailsScreenEvent.OnEditClicked -> onEditClicked()
        }
    }

    private fun onEnterScreen(carId: Long) {
        load(carId)
    }


    private fun onEditClicked() {
        viewModelScope.launch {
            _effect.emit(CarDetailsScreenEffect.NavigateToCarEditScreen)
        }
    }

    private fun load(carId: Long) = viewModelScope.launch {
        try {
            _state.value = CarDetailsScreenState.Loading

            carsRepository.getById(carId).collectLatest { car ->
                _state.value = CarDetailsScreenState.Successful(car)
            }
        } catch (e: Exception) {
            _state.value = CarDetailsScreenState.Error
        }
    }

}