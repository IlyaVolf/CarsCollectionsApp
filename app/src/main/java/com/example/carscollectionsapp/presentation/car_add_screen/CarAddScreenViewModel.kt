package com.example.carscollectionsapp.presentation.car_add_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carscollectionsapp.domain.CarsRepository
import com.example.carscollectionsapp.domain.entities.Car
import com.example.carscollectionsapp.presentation.car_add_screen.entities.CarAddContainer
import com.example.carscollectionsapp.presentation.car_add_screen.entities.CarAddScreenEffect
import com.example.carscollectionsapp.presentation.car_add_screen.entities.CarAddScreenEvent
import com.example.carscollectionsapp.presentation.car_add_screen.entities.CarAddScreenState
import com.example.carscollectionsapp.presentation.entities.TextFieldState
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
            is CarAddScreenEvent.OnCancelClicked -> onCancelClicked()
            is CarAddScreenEvent.OnCancelConfirmClicked -> onCancelConfirmClicked()
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

    private fun onCancelClicked() = viewModelScope.launch {
        _effect.emit(CarAddScreenEffect.OpenDialog)
    }

    private fun onCancelConfirmClicked() = viewModelScope.launch {
        _effect.emit(CarAddScreenEffect.NavigateBack)
    }


    private fun onSaveClicked() = viewModelScope.launch {
        if (state.value !is CarAddScreenState.Default) {
            return@launch
        }

        val carAddContainer = (state.value as CarAddScreenState.Default).carAddContainer

        val newNameState = getNameState(carAddContainer)
        val newYearState = getYearState(carAddContainer)
        val newEngineCapacityState = getEngineCapacityState(carAddContainer)

        if (!(newNameState == TextFieldState.OK &&
                    newYearState == TextFieldState.OK &&
                    newEngineCapacityState == TextFieldState.OK)
        ) {
            _state.update {
                CarAddScreenState.Default(
                    (it as CarAddScreenState.Default).carAddContainer.copy(
                        nameState = newNameState,
                        yearState = newYearState,
                        engineCapacityState = newEngineCapacityState
                    )
                )
            }

            return@launch

        }

        save(carAddContainer)
    }

    private suspend fun save(carAddContainer: CarAddContainer) {
        try {
            _state.value = CarAddScreenState.Saving
            val car = getCarInstance(carAddContainer)
            carsRepository.addCar(car)
            _effect.emit(CarAddScreenEffect.NavigateBackOnSuccessAdding)
        } catch (e: Exception) {
            _state.value = CarAddScreenState.Error(e)
        }
    }

    private fun getNameState(carAddContainer: CarAddContainer): TextFieldState {
        if (carAddContainer.nameString.isBlank()) {
            return TextFieldState.EMPTY
        }

        return TextFieldState.OK
    }

    private fun getYearState(carAddContainer: CarAddContainer): TextFieldState {
        if (carAddContainer.yearString.isBlank()) {
            return TextFieldState.EMPTY
        }

        if (carAddContainer.yearString.toIntOrNull() == null) {
            return TextFieldState.INVALID
        }

        return TextFieldState.OK
    }

    private fun getEngineCapacityState(carAddContainer: CarAddContainer): TextFieldState {
        if (carAddContainer.engineCapacityString.isBlank()) {
            return TextFieldState.EMPTY
        }

        if (carAddContainer.engineCapacityString.toFloatOrNull() == null) {
            return TextFieldState.INVALID
        }

        return TextFieldState.OK
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