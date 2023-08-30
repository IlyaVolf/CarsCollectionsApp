package com.example.carscollectionsapp.presentation.car_details_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.carscollectionsapp.presentation.car_details_screen.entities.CarDetailsScreenEffect
import com.example.carscollectionsapp.presentation.car_details_screen.entities.CarDetailsScreenEvent
import com.example.carscollectionsapp.presentation.car_details_screen.entities.CarDetailsScreenState
import com.example.carscollectionsapp.presentation.car_details_screen.views.CarDetailsScreenLoading
import com.example.carscollectionsapp.presentation.car_details_screen.views.CarDetailsScreenSuccessful
import com.example.carscollectionsapp.utils.collectAsEffect

@Composable
fun CarDetailsScreen(
    carId: Long,
    navController: NavController,
    viewModel: CarDetailsScreenViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(CarDetailsScreenEvent.OnEnterScreen(carId))
    }

    val state = viewModel.state.collectAsState()

    viewModel.effect.collectAsEffect { effect ->
        when (effect) {
            is CarDetailsScreenEffect.NavigateToCarEditScreen -> {
                /*navController.navigate(
                    CarsAppScreens.CarDetailsScreen.passArguments(effect.carId)
                )*/
            }

            else -> {}
        }
    }


    when (state.value) {
        is CarDetailsScreenState.Loading -> CarDetailsScreenLoading()
        is CarDetailsScreenState.Successful -> CarDetailsScreenSuccessful(
            car = (state.value as CarDetailsScreenState.Successful).car
        )

        else -> {}
    }

}