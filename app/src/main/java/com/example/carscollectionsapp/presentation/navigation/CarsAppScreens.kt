package com.example.carscollectionsapp.presentation.navigation

sealed class CarsAppScreens(val route: String) {

    data object CarsListScreen : CarsAppScreens(route = "main_screen")

    data object CarAddScreen : CarsAppScreens(route = "car_add_screen")

    data object CarEditScreen : CarsAppScreens(route = "car_edit_screen/{${carIdArgument}}") {

        fun passArguments(id: Long): String {
            return this.route.replace("{${carIdArgument}}", id.toString())
        }
    }

    data object CarDetailsScreen : CarsAppScreens(route = "car_details_screen/{${carIdArgument}}") {
        fun passArguments(id: Long): String {
            return this.route.replace("{${carIdArgument}}", id.toString())
        }
    }

    data object SettingsScreen : CarsAppScreens(route = "settings_screen")


    companion object {
        const val carIdArgument = "car_id"
    }

}