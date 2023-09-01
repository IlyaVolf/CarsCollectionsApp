package com.example.carscollectionsapp.domain.entities

import kotlin.time.Duration

data class SubscriptionDetails(
    val name: String,
    val price: Float,
    val description: String,
    val subscriptionPeriod: Duration
)