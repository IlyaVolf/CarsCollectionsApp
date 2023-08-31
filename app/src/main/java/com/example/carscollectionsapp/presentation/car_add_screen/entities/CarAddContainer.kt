package com.example.carscollectionsapp.presentation.car_add_screen.entities

data class CarAddContainer(
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

        fun newInstance(): CarAddContainer = CarAddContainer(
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