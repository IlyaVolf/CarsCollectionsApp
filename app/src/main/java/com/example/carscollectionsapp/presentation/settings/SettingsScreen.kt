package com.example.carscollectionsapp.presentation.settings

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.carscollectionsapp.R
import com.example.carscollectionsapp.presentation.customView.CustomButton
import com.example.carscollectionsapp.presentation.settings.entities.SettingsScreenEffect
import com.example.carscollectionsapp.presentation.settings.entities.SettingsScreenEvent
import com.example.carscollectionsapp.utils.collectAsEffect

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsScreenViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    viewModel.effect.collectAsEffect { effect ->
        val currentState = lifecycleOwner.lifecycle.currentState
        if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            when (effect) {
                SettingsScreenEffect.Reset -> {
                    Toast.makeText(context, "Reset", Toast.LENGTH_SHORT).show()
                }

                SettingsScreenEffect.DBInited -> {
                    Toast.makeText(context, "Db Inited", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CustomButton(
            onClick = { viewModel.onEvent(SettingsScreenEvent.OnResetClicked) },
            text = stringResource(id = R.string.reset),
            modifier = Modifier.fillMaxWidth(0.5f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomButton(
            onClick = { viewModel.onEvent(SettingsScreenEvent.OnInitDbClicked) },
            text = stringResource(id = R.string.init_db),
            modifier = Modifier.fillMaxWidth(0.5f)
        )
    }

}