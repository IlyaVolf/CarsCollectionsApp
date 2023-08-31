package com.example.carscollectionsapp.presentation.main_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.carscollectionsapp.R
import com.example.carscollectionsapp.presentation.customView.CustomTextField
import com.example.carscollectionsapp.presentation.entities.TextFieldState
import com.example.carscollectionsapp.presentation.main_screen.entities.MainScreenEffect
import com.example.carscollectionsapp.presentation.main_screen.entities.MainScreenEvent
import com.example.carscollectionsapp.presentation.main_screen.entities.MainScreenState
import com.example.carscollectionsapp.presentation.main_screen.views.MainScreenLoading
import com.example.carscollectionsapp.presentation.main_screen.views.MainScreenSuccessfulState
import com.example.carscollectionsapp.presentation.navigation.CarsAppScreens
import com.example.carscollectionsapp.utils.collectAsEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainScreenViewModel = hiltViewModel(),
) {

    val state = viewModel.state.collectAsState()
    val search = viewModel.searchQuery.collectAsState()

    viewModel.effect.collectAsEffect { effect ->
        when (effect) {
            MainScreenEffect.NavigateToCarAddScreen -> {
                navController.navigate(
                    CarsAppScreens.CarAddScreen.route
                )
            }
            is MainScreenEffect.NavigateToCarDetailsScreen -> {
                navController.navigate(
                    CarsAppScreens.CarDetailsScreen.passArguments(effect.carId)
                )
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(MainScreenEvent.AddNewCarClicked) },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background,
                modifier = Modifier.size(80.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.description)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues = paddingValues)
        ) {

            Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically) {
                CustomTextField(
                    modifier = Modifier.wrapContentWidth(),
                    label = stringResource(R.string.search),
                    value = search.value,
                    singleLine = true,
                    onValueChange = { viewModel.searchQuery.value = it },
                    state = TextFieldState.OK,
                    isNecessaryField = false
                )

                Spacer(modifier = Modifier.width(16.dp))

                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp).padding(8.dp).clickable {

                    },
                    tint = MaterialTheme.colorScheme.primary
                )

            }

            when (state.value) {
                is MainScreenState.Loading -> MainScreenLoading()
                is MainScreenState.Successful -> MainScreenSuccessfulState(
                    cars = (state.value as MainScreenState.Successful).cars,
                    onCarClicked = { carId ->
                        viewModel.onEvent(MainScreenEvent.OnCarClicked(carId))
                    }
                )

                else -> {
                }
            }
        }
    }

}