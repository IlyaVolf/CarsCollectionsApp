package com.example.carscollectionsapp.presentation.subscription.entities

import com.example.carscollectionsapp.domain.entities.SubscriptionState

sealed class SubscriptionScreenState {

    data object Loading : SubscriptionScreenState()

    data class Default(val subscriptionState: SubscriptionState) : SubscriptionScreenState()

    data class Error(val error: Throwable) : SubscriptionScreenState()

}