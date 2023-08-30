package com.example.carscollectionsapp.presentation.car_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carscollectionsapp.domain.CarsRepository
import com.example.carscollectionsapp.presentation.car_details_screen.states.CarDetailsScreenEffect
import com.example.carscollectionsapp.presentation.car_details_screen.states.CarDetailsScreenState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CarDetailsScreenViewModel @AssistedInject constructor(
    @Assisted private val carId: Long,
    private val carsRepository: CarsRepository
) : ViewModel() {

    private val _effect = MutableSharedFlow<CarDetailsScreenEffect>()
    val effect = _effect.asSharedFlow()

    private val _state = MutableStateFlow<CarDetailsScreenState>(CarDetailsScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        load()
    }

    fun tryAgain() {
        load()
    }

    private fun load() = viewModelScope.launch {
        try {
            _state.value = CarDetailsScreenState.Loading

            carsRepository.getById(carId).collect { cars ->
                _state.value = CarDetailsScreenState.Successful(cars)
            }
        } catch (e: Exception) {
            _state.value = CarDetailsScreenState.Error
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(carId: Long): CarDetailsScreenViewModel
    }

}