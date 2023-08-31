package com.example.carscollectionsapp.presentation.main_screen.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.carscollectionsapp.presentation.customView.ProgressIndicator

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenLoading(
    modifier: Modifier = Modifier,
    onCarClicked: (Long) -> Unit = {},
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ProgressIndicator()
    }
}