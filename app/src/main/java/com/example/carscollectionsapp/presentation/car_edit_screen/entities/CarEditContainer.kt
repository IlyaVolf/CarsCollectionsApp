package com.example.carscollectionsapp.presentation.car_edit_screen.entities

data class CarEditContainer(
    val nameString: String,
    val photoString: String?,
    val yearString: String,
    val engineCapacityString: String,
    val nameState: NameState,
    val yearState: YearState,
    val engineCapacityState: EngineCapacityState
) {

    enum class NameState {
        OK, EMPTY
    }

    enum class YearState {
        OK, EMPTY, INVALID
    }

    enum class EngineCapacityState {
        OK, EMPTY, INVALID
    }

    companion object {

        fun newInstance(): CarEditContainer = CarEditContainer(
            nameString = "",
            photoString = "",
            yearString = "",
            engineCapacityString = "",
            nameState = NameState.EMPTY,
            yearState = YearState.EMPTY,
            engineCapacityState = EngineCapacityState.EMPTY
        )

    }

}