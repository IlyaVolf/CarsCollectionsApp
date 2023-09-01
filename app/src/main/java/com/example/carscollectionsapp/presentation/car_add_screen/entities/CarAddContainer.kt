package com.example.carscollectionsapp.presentation.car_add_screen.entities

import com.example.carscollectionsapp.presentation.entities.TextFieldState

data class CarAddContainer(
    val nameString: String,
    val photoString: String?,
    val yearString: String,
    val engineCapacityString: String,
    val nameState: TextFieldState,
    val yearState: TextFieldState,
    val engineCapacityState: TextFieldState
) {
    companion object {

        fun newInstance(): CarAddContainer = CarAddContainer(
            nameString = "",
            photoString = "",
            yearString = "",
            engineCapacityString = "",
            nameState = TextFieldState.INIT,
            yearState = TextFieldState.INIT,
            engineCapacityState = TextFieldState.INIT
        )

    }

}