package com.example.carscollectionsapp.presentation.car_details_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.carscollectionsapp.R
import com.example.carscollectionsapp.presentation.car_details_screen.entities.CarDetailsScreenEffect
import com.example.carscollectionsapp.presentation.car_details_screen.entities.CarDetailsScreenEvent
import com.example.carscollectionsapp.presentation.car_details_screen.entities.CarDetailsScreenState
import com.example.carscollectionsapp.presentation.car_details_screen.views.CarDetailsScreenLoading
import com.example.carscollectionsapp.presentation.car_details_screen.views.CarDetailsScreenSuccessful
import com.example.carscollectionsapp.utils.collectAsEffect

@OptIn(ExperimentalMaterial3Api::class)
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
               /* navController.navigate(
                    CarsAppScreens.CarAddEditScreen.passArguments(effect.carId)
                )*/
            }

            else -> {}
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(CarDetailsScreenEvent.OnEditClicked) },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background,
                modifier = Modifier.size(80.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.description)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->

        when (state.value) {
            is CarDetailsScreenState.Loading -> CarDetailsScreenLoading(
                modifier = Modifier.padding(paddingValues = paddingValues)
            )

            is CarDetailsScreenState.Successful -> CarDetailsScreenSuccessful(
                car = (state.value as CarDetailsScreenState.Successful).car,
                modifier = Modifier.padding(paddingValues = paddingValues)
            )

            else -> {}
        }

    }

}