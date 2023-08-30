package com.example.carscollectionsapp.presentation.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.carscollectionsapp.presentation.main_screen.entities.MainScreenState
import com.example.carscollectionsapp.presentation.main_screen.views.MainScreenLoading
import com.example.carscollectionsapp.presentation.main_screen.views.MainScreenSuccessfulState
import com.example.carscollectionsapp.presentation.navigation.CarsAppScreens
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainScreenViewModel = hiltViewModel(),
) {

    val state = viewModel.state.collectAsState()
    val search = viewModel.searchQuery.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            // on below line we are specifying value
            // for our message text field.
            value = search.value,
            // on below line we are adding on
            // value change for text field.
            onValueChange = { viewModel.searchQuery.value = it },
            // on below line we are adding place holder
            // as text as "Enter your email"
            placeholder = {
                Text(
                    text = "Enter your location to search",
                    color = Color(0xFF383838)
                )
            },
            // on below line we are adding modifier to it
            // and adding padding to it and filling max width
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(64.dp),
            // on below line we are adding text style
            // specifying color and font size to it.
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            // on below line we are adding single line to it.
            singleLine = true,
        )

        when (state.value) {
            is MainScreenState.Loading -> MainScreenLoading()
            is MainScreenState.Successful -> MainScreenSuccessfulState(
                cars = (state.value as MainScreenState.Successful).cars
            )

            else -> {}
        }
    }

}