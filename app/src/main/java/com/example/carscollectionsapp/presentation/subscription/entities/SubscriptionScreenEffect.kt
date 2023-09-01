package com.example.carscollectionsapp.presentation.subscription.entities

sealed class SubscriptionScreenEffect {

    data object NavigateBackOnSuccessPurchasing : SubscriptionScreenEffect()

    data object NavigateBack : SubscriptionScreenEffect()


}