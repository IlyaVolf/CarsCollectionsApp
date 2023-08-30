package com.example.carscollectionsapp.presentation.car_add_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carscollectionsapp.domain.CarsRepository
import com.example.carscollectionsapp.domain.entities.Car
import com.example.carscollectionsapp.presentation.car_add_screen.entities.CarAddData
import com.example.carscollectionsapp.presentation.car_add_screen.entities.CarAddScreenEffect
import com.example.carscollectionsapp.presentation.car_add_screen.entities.CarAddScreenState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar

class CarAddScreenViewModel @AssistedInject constructor(
    @Assisted private val carId: Long,
    private val carsRepository: CarsRepository
) : ViewModel() {

    private val mode = if (carId == DEFAULT_ID) {
        Mode.Add
    } else {
        Mode.Edit
    }

    private val _effect = MutableSharedFlow<CarAddScreenEffect>()
    val effect = _effect.asSharedFlow()

    private val _state = MutableStateFlow<CarAddScreenState>(CarAddScreenState.Saving)
    val state = _state.asStateFlow()


    init {
        load()
    }

    fun tryAgain() {
        load()
    }

    fun save(carAddData: CarAddData) {

        if (!validate()) {
            return
        }

        viewModelScope.launch {
            try {
                _state.value = CarAddScreenState.Saving
                val car = getCarInstance(carAddData)
                when (mode) {
                    Mode.Add -> {
                        carsRepository.addCar(car)
                    }
                    Mode.Edit -> {
                        carsRepository.updateCar(car)
                    }
                }
                _state.value = CarAddScreenState.Saving
            } catch (e: Exception) {
                _state.value = CarAddScreenState.Error(e)
            }
        }
    }

    private fun load() = viewModelScope.launch {
        if (mode == Mode.Add) {
            _state.value = CarAddScreenState.Successful(null)
            return@launch
        }

        try {
            _state.value = CarAddScreenState.Loading

            carsRepository.getById(carId).collectLatest { car ->
                _state.value = CarAddScreenState.Successful(car)
            }
        } catch (e: Exception) {
            _state.value = CarAddScreenState.Error(e)
        }
    }

    // TODO Validation
    private fun validate(): Boolean {
        return true
    }

    private fun getCarInstance(carAddData: CarAddData): Car = Car(
        id = if (carId != DEFAULT_ID) carId else 0,
        name = carAddData.name,
        photo = carAddData.photo,
        year = carAddData.year,
        engineCapacity = carAddData.engineCapacity,
        dateAdded = Calendar.getInstance().timeInMillis
    )

    @AssistedFactory
    interface Factory {
        fun create(cocktailId: Long): CarAddScreenViewModel
    }

    companion object {
        enum class Mode { Add, Edit }

        const val DEFAULT_ID = -1L
    }

}