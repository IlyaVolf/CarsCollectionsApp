package com.example.carscollectionsapp.presentation.car_edit_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carscollectionsapp.domain.CarsRepository
import com.example.carscollectionsapp.domain.entities.Car
import com.example.carscollectionsapp.presentation.car_edit_screen.entities.CarEditContainer
import com.example.carscollectionsapp.presentation.car_edit_screen.entities.CarEditScreenEffect
import com.example.carscollectionsapp.presentation.car_edit_screen.entities.CarEditScreenEvent
import com.example.carscollectionsapp.presentation.car_edit_screen.entities.CarEditScreenState
import com.example.carscollectionsapp.presentation.entities.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class CarEditScreenViewModel @Inject constructor(
    private val carsRepository: CarsRepository
) : ViewModel() {

    private var carId by Delegates.notNull<Long>()
    private var car: Car? = null

    private val _effect = MutableSharedFlow<CarEditScreenEffect>()
    val effect = _effect.asSharedFlow()

    private val _state = MutableStateFlow<CarEditScreenState>(
        CarEditScreenState.Loading
    )
    val state = _state.asStateFlow()

    fun onEvent(event: CarEditScreenEvent) {
        when (event) {
            is CarEditScreenEvent.OnEnterScreen -> onEnterScreen(event.carId)
            is CarEditScreenEvent.OnNameChanged -> onNameChanged(event.newNameString)
            is CarEditScreenEvent.OnYearChanged -> onYearChanged(event.newYearString)
            is CarEditScreenEvent.OnPhotoChanged -> onPhotoChanged(event.newPhotoUriString)
            is CarEditScreenEvent.OnEngineCapacityChanged -> onEngineCapacityChanged(event.newEngineCapacityString)
            is CarEditScreenEvent.OnSaveClicked -> onSaveClicked()
            is CarEditScreenEvent.OnCancelClicked -> onCancelClicked()
            is CarEditScreenEvent.OnDeleteClicked -> onDeleteClicked()
            is CarEditScreenEvent.OnCancelConfirmClicked -> onCancelConfirmClicked()
            is CarEditScreenEvent.OnDeleteConfirmClicked -> onDeleteConfirmClicked()
        }
    }

    private fun load() = viewModelScope.launch {
        try {
            _state.value = CarEditScreenState.Loading

            carsRepository.getById(carId).collectLatest {
                car = it
                val carEditContainer = CarEditContainer.newInstance(it)
                _state.value = CarEditScreenState.Successful(carEditContainer)
            }
        } catch (e: Exception) {
            _state.value = CarEditScreenState.Error(e)
        }
    }

    private fun onEnterScreen(id: Long) = viewModelScope.launch {
        carId = id
        load()
    }

    private fun onNameChanged(newNameString: String) = viewModelScope.launch {
        if (state.value !is CarEditScreenState.Successful) {
            return@launch
        }

        _state.update {
            CarEditScreenState.Successful(
                (it as CarEditScreenState.Successful).carAddContainer.copy(
                    nameString = newNameString
                )
            )
        }
    }

    private fun onPhotoChanged(newPhotoUriString: String?) = viewModelScope.launch {
        if (state.value !is CarEditScreenState.Successful) {
            return@launch
        }

        _state.update {
            CarEditScreenState.Successful(
                (it as CarEditScreenState.Successful).carAddContainer.copy(
                    photoString = newPhotoUriString
                )
            )
        }
    }

    private fun onYearChanged(newYearString: String) = viewModelScope.launch {
        if (state.value !is CarEditScreenState.Successful) {
            return@launch
        }

        _state.update {
            CarEditScreenState.Successful(
                (it as CarEditScreenState.Successful).carAddContainer.copy(
                    yearString = newYearString
                )
            )
        }
    }

    private fun onEngineCapacityChanged(newEngineCapacityString: String) = viewModelScope.launch {
        if (state.value !is CarEditScreenState.Successful) {
            return@launch
        }

        _state.update {
            CarEditScreenState.Successful(
                (it as CarEditScreenState.Successful).carAddContainer.copy(
                    engineCapacityString = newEngineCapacityString
                )
            )
        }
    }

    private fun onCancelClicked() = viewModelScope.launch {
        _effect.emit(CarEditScreenEffect.OpenCancelDialog)
    }

    private fun onCancelConfirmClicked() = viewModelScope.launch {
        _effect.emit(CarEditScreenEffect.NavigateBack)
    }


    private fun onSaveClicked() {
        if (state.value !is CarEditScreenState.Successful) {
            return
        }

        val carAddContainer = (state.value as CarEditScreenState.Successful).carAddContainer

        val newNameState = getNameState(carAddContainer)
        val newYearState = getYearState(carAddContainer)
        val newEngineCapacityState = getEngineCapacityState(carAddContainer)

        if (!(newNameState == TextFieldState.OK &&
                    newYearState == TextFieldState.OK &&
                    newEngineCapacityState == TextFieldState.OK)
        ) {
            _state.update {
                CarEditScreenState.Successful(
                    (it as CarEditScreenState.Successful).carAddContainer.copy(
                        nameState = newNameState,
                        yearState = newYearState,
                        engineCapacityState = newEngineCapacityState
                    )
                )
            }

            return

        }

        save(carAddContainer)
    }

    private fun save(carAddContainer: CarEditContainer) = viewModelScope.launch {
        try {
            _state.value = CarEditScreenState.Saving
            val car = getCarInstance(carAddContainer) ?: return@launch
            carsRepository.updateCar(car)
            _effect.emit(CarEditScreenEffect.NavigateBackOnSuccessAdding)
        } catch (e: Exception) {
            _state.value = CarEditScreenState.Error(e)
        }
    }

    private fun onDeleteClicked() = viewModelScope.launch {
        _effect.emit(CarEditScreenEffect.OpenDeleteDialog)
    }

    private fun onDeleteConfirmClicked() = viewModelScope.launch {
        try {
            _state.value = CarEditScreenState.Saving
            val car = car ?: return@launch
            carsRepository.deleteCar(car)
            _effect.emit(CarEditScreenEffect.NavigateBackOnSuccessDeleting)
        } catch (e: Exception) {
            _state.value = CarEditScreenState.Error(e)
        }
    }

    private fun getNameState(carAddContainer: CarEditContainer): TextFieldState {
        if (carAddContainer.nameString.isBlank()) {
            return TextFieldState.EMPTY
        }

        return TextFieldState.OK
    }

    private fun getYearState(carAddContainer: CarEditContainer): TextFieldState {
        if (carAddContainer.yearString.isBlank()) {
            return TextFieldState.EMPTY
        }

        if (carAddContainer.yearString.toIntOrNull() == null) {
            return TextFieldState.INVALID
        }

        return TextFieldState.OK
    }

    private fun getEngineCapacityState(carAddContainer: CarEditContainer): TextFieldState {
        if (carAddContainer.engineCapacityString.isBlank()) {
            return TextFieldState.EMPTY
        }

        if (carAddContainer.engineCapacityString.toFloatOrNull() == null) {
            return TextFieldState.INVALID
        }

        return TextFieldState.OK
    }

    private fun getCarInstance(carAddContainer: CarEditContainer): Car? {
        val car = car ?: return null

        return Car(
            id = car.id,
            name = carAddContainer.nameString,
            photo = carAddContainer.photoString,
            year = carAddContainer.yearString.toInt(),
            engineCapacity = carAddContainer.engineCapacityString.toFloat(),
            dateAdded = car.dateAdded
        )
    }

}