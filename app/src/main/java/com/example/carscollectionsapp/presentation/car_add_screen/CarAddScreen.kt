package com.example.carscollectionsapp.presentation.car_add_screen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.carscollectionsapp.R
import com.example.carscollectionsapp.presentation.car_add_screen.entities.CarAddScreenEffect
import com.example.carscollectionsapp.presentation.car_add_screen.entities.CarAddScreenEvent
import com.example.carscollectionsapp.presentation.car_add_screen.entities.CarAddScreenState
import com.example.carscollectionsapp.presentation.car_add_screen.views.CarAddScreenDefault
import com.example.carscollectionsapp.presentation.subscription.entities.SubscriptionScreenEvent
import com.example.carscollectionsapp.utils.collectAsEffect

@Composable
fun CarAddScreen(
    navController: NavController,
    viewModel: CarAddScreenViewModel = hiltViewModel(),
) {

    val state = viewModel.state.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    val openDialog = remember { mutableStateOf(false) }

    BackHandler {
       viewModel.onEvent(CarAddScreenEvent.OnCancelClicked)
    }

    viewModel.effect.collectAsEffect { effect ->
        val currentState = lifecycleOwner.lifecycle.currentState
        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            when (effect) {
                CarAddScreenEffect.NavigateBack -> {
                    navController.popBackStack()
                }

                CarAddScreenEffect.NavigateBackOnSuccessAdding -> {
                    navController.popBackStack()
                }

                CarAddScreenEffect.OpenDialog -> {
                    openDialog.value = true
                }
            }
        }
    }

    when (state.value) {
        is CarAddScreenState.Default -> CarAddScreenDefault(
            carAddContainer = (state.value as CarAddScreenState.Default).carAddContainer,
            onAction = { event ->
                viewModel.onEvent(event)
            },
        )

        else -> {}
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(CarAddScreenEvent.OnCancelConfirmClicked)
                        openDialog.value = false
                    }
                ) {
                    Text(text = stringResource(R.string.delete))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            },
            title = { Text(text = stringResource(R.string.cancel)) },
            text = { Text(text = stringResource(R.string.warning)) }
        )
    }

}