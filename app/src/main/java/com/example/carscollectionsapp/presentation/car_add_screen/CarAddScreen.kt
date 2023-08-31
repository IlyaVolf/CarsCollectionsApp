package com.example.carscollectionsapp.presentation.car_add_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.carscollectionsapp.presentation.car_add_screen.entities.CarAddScreenEffect
import com.example.carscollectionsapp.presentation.car_add_screen.entities.CarAddScreenState
import com.example.carscollectionsapp.presentation.car_add_screen.views.CarAddScreenSuccessful
import com.example.carscollectionsapp.utils.collectAsEffect

@Composable
fun CarAddScreen(
    navController: NavController,
    viewModel: CarAddScreenViewModel = hiltViewModel(),
) {

    val state = viewModel.state.collectAsState()

    viewModel.effect.collectAsEffect { effect ->
        when (effect) {
            CarAddScreenEffect.NavigateBack -> {
                navController.popBackStack()
            }

            CarAddScreenEffect.NavigateBackOnSuccessAdding -> {
                navController.popBackStack()
            }
        }
    }

    when (state.value) {
        is CarAddScreenState.Default -> CarAddScreenSuccessful(
            carAddContainer = (state.value as CarAddScreenState.Default).carAddContainer,
            onAction = { event ->
                viewModel.onEvent(event)
            },
        )

        else -> {}
    }

}