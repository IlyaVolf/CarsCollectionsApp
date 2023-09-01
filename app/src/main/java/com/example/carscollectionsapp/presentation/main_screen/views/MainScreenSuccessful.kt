package com.example.carscollectionsapp.presentation.main_screen.views

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.carscollectionsapp.domain.entities.Car

@Composable
fun MainScreenSuccessfulState(
    cars: List<Car>,
    modifier: Modifier = Modifier,
    onCarClicked: (Long) -> Unit = {},
) {

    val configuration = LocalConfiguration.current
    val gridCellsCount = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> 2
        else -> 1
    }

    LazyVerticalGrid(columns = GridCells.Fixed(gridCellsCount), modifier = modifier.padding(bottom = 8.dp)) {
        items(cars, key = { it.id }) {
            CarItem(
                car = it,
                onCarClicked = onCarClicked
            )
        }
    }

}