package com.example.carscollectionsapp.presentation.car_edit_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.carscollectionsapp.R
import com.example.carscollectionsapp.presentation.car_details_screen.entities.CarDetailsScreenEvent
import com.example.carscollectionsapp.presentation.car_edit_screen.entities.CarEditScreenEffect
import com.example.carscollectionsapp.presentation.car_edit_screen.entities.CarEditScreenEvent
import com.example.carscollectionsapp.presentation.car_edit_screen.entities.CarEditScreenState
import com.example.carscollectionsapp.presentation.car_edit_screen.views.CarEditScreenLoading
import com.example.carscollectionsapp.presentation.car_edit_screen.views.CarEditScreenSuccessful
import com.example.carscollectionsapp.presentation.navigation.CarsAppScreens
import com.example.carscollectionsapp.utils.collectAsEffect

@Composable
fun CarEditScreen(
    carId: Long,
    navController: NavController,
    viewModel: CarEditScreenViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(CarEditScreenEvent.OnEnterScreen(carId))
    }

    val state = viewModel.state.collectAsState()

    viewModel.effect.collectAsEffect { effect ->
        when (effect) {
            CarEditScreenEffect.NavigateBack -> {
                navController.popBackStack()
            }

            CarEditScreenEffect.NavigateBackOnSuccessAdding -> {
                navController.popBackStack()
            }

            CarEditScreenEffect.NavigateBackOnSuccessDeleting -> {
                navController.popBackStack(CarsAppScreens.CarsListScreen.route, false)
            }
        }
    }

    when (state.value) {
        is CarEditScreenState.Loading -> CarEditScreenLoading()
        is CarEditScreenState.Successful -> CarEditScreenSuccessful(
            carAddContainer = (state.value as CarEditScreenState.Successful).carAddContainer,
            onAction = { event ->
                viewModel.onEvent(event)
            },
        )

        else -> {}
    }

}