package com.example.carscollectionsapp.presentation.subscription.entities

import com.example.carscollectionsapp.domain.entities.SubscriptionDetails

sealed class SubscriptionScreenState {

    data object Loading : SubscriptionScreenState()

    data class Successful(val subscriptionDetails: SubscriptionDetails) : SubscriptionScreenState()

    data class Error(val error: Throwable) : SubscriptionScreenState()

}