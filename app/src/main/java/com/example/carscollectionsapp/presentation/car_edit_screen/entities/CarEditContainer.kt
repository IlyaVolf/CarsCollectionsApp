package com.example.carscollectionsapp.presentation.car_edit_screen.entities

import com.example.carscollectionsapp.domain.entities.Car
import com.example.carscollectionsapp.presentation.entities.TextFieldState

data class CarEditContainer(
    val nameString: String,
    val photoString: String?,
    val yearString: String,
    val engineCapacityString: String,
    val nameState: TextFieldState,
    val yearState: TextFieldState,
    val engineCapacityState: TextFieldState
) {
    companion object {

        fun newInstance(car: Car): CarEditContainer = CarEditContainer(
            nameString = car.name,
            photoString = car.photo,
            yearString = car.year.toString(),
            engineCapacityString = car.engineCapacity.toString(),
            nameState = TextFieldState.OK,
            yearState = TextFieldState.OK,
            engineCapacityState = TextFieldState.OK
        )

    }

}