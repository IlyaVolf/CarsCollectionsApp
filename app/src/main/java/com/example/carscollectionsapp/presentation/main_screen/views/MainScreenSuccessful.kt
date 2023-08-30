package com.example.carscollectionsapp.presentation.main_screen.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.carscollectionsapp.domain.entities.Car

@Composable
fun MainScreenSuccessfulState(
    cars: List<Car>,
    modifier: Modifier = Modifier,
    onCarClicked: (Long) -> Unit = {},
) {
    LazyVerticalGrid(columns = GridCells.Fixed(1), modifier = modifier) {
        items(cars, key = { it.id }) {
            CarItem(
                car = it,
                modifier = Modifier.padding(vertical = 8.dp),
                onCarClicked = onCarClicked
            )
        }
    }
}