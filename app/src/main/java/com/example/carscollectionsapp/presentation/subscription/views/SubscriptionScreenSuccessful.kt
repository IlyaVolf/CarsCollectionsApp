package com.example.carscollectionsapp.presentation.subscription.views

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.carscollectionsapp.presentation.customView.ProgressIndicator
import com.example.carscollectionsapp.presentation.subscription.entities.SubscriptionScreenEffect
import com.example.carscollectionsapp.presentation.subscription.entities.SubscriptionScreenState
import com.example.carscollectionsapp.utils.collectAsEffect

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SubscriptionScreenSuccessful(
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