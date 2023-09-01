package com.example.carscollectionsapp.presentation.car_edit_screen

import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.carscollectionsapp.R
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
    val lifecycleOwner = LocalLifecycleOwner.current
    val openCancelDialog = remember { mutableStateOf(false) }
    val openDeleteDialog = remember { mutableStateOf(false) }

    BackHandler {
        viewModel.onEvent(CarEditScreenEvent.OnCancelClicked)
    }

    viewModel.effect.collectAsEffect { effect ->
        val currentState = lifecycleOwner.lifecycle.currentState
        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
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

                CarEditScreenEffect.OpenCancelDialog -> {
                    openCancelDialog.value = true
                }

                CarEditScreenEffect.OpenDeleteDialog -> {
                    openDeleteDialog.value = true
                }
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

    if (openCancelDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openCancelDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(CarEditScreenEvent.OnCancelConfirmClicked)
                        openCancelDialog.value = false
                    }
                ) {
                    Text(text = stringResource(R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openCancelDialog.value = false
                    }
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            },
            title = { Text(text = stringResource(R.string.cancel)) },
            text = { Text(text = stringResource(R.string.cancel_warning)) }
        )
    }

    if (openDeleteDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDeleteDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(CarEditScreenEvent.OnDeleteConfirmClicked)
                        openDeleteDialog.value = false
                    }
                ) {
                    Text(text = stringResource(R.string.delete))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDeleteDialog.value = false
                    }
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            },
            title = { Text(text = stringResource(R.string.delete)) },
            text = { Text(text = stringResource(R.string.delete_warning)) }
        )
    }

}