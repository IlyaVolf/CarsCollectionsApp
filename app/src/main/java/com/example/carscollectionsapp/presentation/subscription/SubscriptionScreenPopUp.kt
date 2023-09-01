package com.example.carscollectionsapp.presentation.subscription

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.carscollectionsapp.R
import com.example.carscollectionsapp.presentation.subscription.entities.SubscriptionScreenEffect
import com.example.carscollectionsapp.presentation.subscription.entities.SubscriptionScreenEvent
import com.example.carscollectionsapp.presentation.subscription.entities.SubscriptionScreenState
import com.example.carscollectionsapp.utils.collectAsEffect

@Composable
fun SubscriptionScreenPopUp(
    openDialog: MutableState<Boolean>,
    viewModel: SubscriptionViewModel = hiltViewModel(),
) {

    val state = viewModel.state.collectAsState()

    viewModel.effect.collectAsEffect { effect ->
        when (effect) {
            SubscriptionScreenEffect.CloseDialog -> {
                openDialog.value = false
            }
            SubscriptionScreenEffect.CloseDialogOnSuccessPurchasing -> {
                openDialog.value = false
            }
        }
    }

    if (openDialog.value) {
        when (state.value) {
            is SubscriptionScreenState.Successful -> {
                val subscriptionDetails =
                    (state.value as SubscriptionScreenState.Successful).subscriptionDetails

                val text = StringBuilder()
                text.append(subscriptionDetails.description)
                text.append("\n")
                text.append("${stringResource(R.string.price)}: ${subscriptionDetails.price}")
                text.append("\n")
                text.append("${stringResource(R.string.subscriptionPeriod)}: ${subscriptionDetails.subscriptionPeriod}")

                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.onEvent(SubscriptionScreenEvent.OnPurchaseSubscriptionClicked)
                            }
                        ) {
                            Text(text = stringResource(R.string.buy))
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                viewModel.onEvent(SubscriptionScreenEvent.OnRejectPurchaseSubscriptionClicked)
                            }
                        ) {
                            Text(text = stringResource(R.string.no_thanks))
                        }
                    },
                    title = { Text(text = subscriptionDetails.name) },
                    text = { Text(text = text.toString()) }
                )
            }

            else -> {}
        }
    }

}