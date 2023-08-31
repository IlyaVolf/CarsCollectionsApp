package com.example.carscollectionsapp.presentation.car_add_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carscollectionsapp.domain.CarsRepository
import com.example.carscollectionsapp.domain.entities.Car
import com.example.carscollectionsapp.presentation.car_add_screen.entities.CarAddContainer
import com.example.carscollectionsapp.presentation.car_add_screen.entities.CarAddScreenEffect
import com.example.carscollectionsapp.presentation.car_add_screen.entities.CarAddScreenEvent
import com.example.carscollectionsapp.presentation.car_add_screen.entities.CarAddScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CarAddScreenViewModel @Inject constructor(
    private val carsRepository: CarsRepository
) : ViewModel() {

    private val _effect = MutableSharedFlow<CarAddScreenEffect>()
    val effect = _effect.asSharedFlow()

    private val _state = MutableStateFlow<CarAddScreenState>(
        CarAddScreenState.Default(
            CarAddContainer.newInstance()
        )
    )
    val state = _state.asStateFlow()

    fun onEvent(event: CarAddScreenEvent) {
        when (event) {
            is CarAddScreenEvent.OnNameChanged -> onNameChanged(event.newNameString)
            is CarAddScreenEvent.OnYearChanged -> onYearChanged(event.newYearString)
            is CarAddScreenEvent.OnPhotoChanged -> onPhotoChanged(event.newPhotoUriString)
            is CarAddScreenEvent.OnEngineCapacityChanged -> onEngineCapacityChanged(event.newEngineCapacityString)
            is CarAddScreenEvent.OnSaveClicked -> onSaveClicked()
        }
    }

    private fun onNameChanged(newNameString: String) = viewModelScope.launch {
        if (state.value !is CarAddScreenState.Default) {
            return@launch
        }

        _state.update {
            CarAddScreenState.Default(
                (it as CarAddScreenState.Default).carAddContainer.copy(
                    nameString = newNameString
                )
            )
        }
    }

    private fun onPhotoChanged(newPhotoUriString: String?) = viewModelScope.launch {
        if (state.value !is CarAddScreenState.Default) {
            return@launch
        }

        _state.update {
            CarAddScreenState.Default(
                (it as CarAddScreenState.Default).carAddContainer.copy(
                    photoString = newPhotoUriString
                )
            )
        }
    }

    private fun onYearChanged(newYearString: String) = viewModelScope.launch {
        if (state.value !is CarAddScreenState.Default) {
            return@launch
        }

        _state.update {
            CarAddScreenState.Default(
                (it as CarAddScreenState.Default).carAddContainer.copy(
                    yearString = newYearString
                )
            )
        }
    }

    private fun onEngineCapacityChanged(newEngineCapacityString: String) = viewModelScope.launch {
        if (state.value !is CarAddScreenState.Default) {
            return@launch
        }

        _state.update {
            CarAddScreenState.Default(
                (it as CarAddScreenState.Default).carAddContainer.copy(
                    engineCapacityString = newEngineCapacityString
                )
            )
        }
    }

    private fun onSaveClicked() = viewModelScope.launch {
        if (state.value !is CarAddScreenState.Default) {
            return@launch
        }

        val carAddContainer = (state.value as CarAddScreenState.Default).carAddContainer
        if (!validate(carAddContainer)) {
            return@launch
        }

        try {
            _state.value = CarAddScreenState.Saving
            val car = getCarInstance(carAddContainer)
            carsRepository.addCar(car)
            _effect.emit(CarAddScreenEffect.NavigateBackOnSuccessAdding)
        } catch (e: Exception) {
            _state.value = CarAddScreenState.Error(e)
        }
    }

    private fun validate(carAddContainer: CarAddContainer): Boolean =
        validateName(carAddContainer) && validateYear(carAddContainer) && validateEngineCapacity(carAddContainer)

    private fun validateName(carAddContainer: CarAddContainer): Boolean {
        if (carAddContainer.nameString.isBlank()) {
            _state.value = CarAddScreenState.Default(carAddContainer.copy(
                nameState = CarAddContainer.NameState.EMPTY
            ))
            return false
        }

        _state.value = CarAddScreenState.Default(carAddContainer.copy(
            nameState = CarAddContainer.NameState.OK
        ))
        return true
    }

    private fun validateYear(carAddContainer: CarAddContainer): Boolean {
        if (carAddContainer.yearString.isBlank()) {
            _state.value = CarAddScreenState.Default(carAddContainer.copy(
                yearState = CarAddContainer.YearState.EMPTY
            ))
            return false
        }

        if (carAddContainer.yearString.toIntOrNull() == null) {
            _state.value = CarAddScreenState.Default(carAddContainer.copy(
                yearState = CarAddContainer.YearState.INVALID
            ))
            return false
        }

        _state.value = CarAddScreenState.Default(carAddContainer.copy(
            nameState = CarAddContainer.NameState.OK
        ))
        return true
    }

    private fun validateEngineCapacity(carAddContainer: CarAddContainer): Boolean {
        if (carAddContainer.engineCapacityString.isBlank()) {
            _state.value = CarAddScreenState.Default(carAddContainer.copy(
                engineCapacityState = CarAddContainer.EngineCapacityState.EMPTY
            ))
            return false
        }

        if (carAddContainer.engineCapacityString.toFloatOrNull() == null) {
            _state.value = CarAddScreenState.Default(carAddContainer.copy(
                engineCapacityState = CarAddContainer.EngineCapacityState.INVALID
            ))
            return false
        }

        _state.value = CarAddScreenState.Default(carAddContainer.copy(
            engineCapacityState = CarAddContainer.EngineCapacityState.OK
        ))
        return true
    }

    private fun getCarInstance(carAddContainer: CarAddContainer): Car = Car(
        id = 0,
        name = carAddContainer.nameString,
        photo = carAddContainer.photoString,
        year = carAddContainer.yearString.toInt(),
        engineCapacity = carAddContainer.engineCapacityString.toFloat(),
        dateAdded = Calendar.getInstance().timeInMillis
    )

}