package com.example.carscollectionsapp.presentation.subscription.entities

sealed class SubscriptionScreenEffect {

    data object CloseDialogOnSuccessPurchasing : SubscriptionScreenEffect()

    data object CloseDialog : SubscriptionScreenEffect()


}