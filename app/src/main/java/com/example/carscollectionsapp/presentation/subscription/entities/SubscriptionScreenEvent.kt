package com.example.carscollectionsapp.presentation.subscription.entities

sealed class SubscriptionScreenEvent {

    data object OnPurchaseSubscriptionClicked : SubscriptionScreenEvent()

    data object OnRejectPurchaseSubscriptionClicked : SubscriptionScreenEvent()


}