package com.example.carscollectionsapp.domain

import com.example.carscollectionsapp.domain.entities.SubscriptionDetails
import com.example.carscollectionsapp.domain.entities.SubscriptionState
import kotlinx.coroutines.flow.StateFlow

interface SubscriptionsRepository {

    val subscriptionStateFlow: StateFlow<SubscriptionState>

    suspend fun getSubscriptionDetails(): SubscriptionDetails

    suspend fun countAsCarDetailsOpened()

    suspend fun countAsCarAddOpened()

    suspend fun purchase()

    suspend fun reset()

}