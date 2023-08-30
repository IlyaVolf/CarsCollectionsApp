package com.example.carscollectionsapp.presentation.navigation

sealed class CarsAppScreens(val route: String) {

    data object CarsListScreen : CarsAppScreens(route = "main_screen")

    data object CarAddingScreen :CarsAppScreens(route = "car_add_screen")

    data object CarDetailsScreen : CarsAppScreens(route = "car_details_screen/{${carIdArgument}}") {
        fun passArguments(id: Long): String {
            return this.route.replace("{${carIdArgument}}", id.toString())
        }
    }


    companion object {
        const val carIdArgument = "car_id"
    }

}