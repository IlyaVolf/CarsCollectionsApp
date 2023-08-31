package com.example.carscollectionsapp.domain

import com.example.carscollectionsapp.domain.entities.SubscriptionState
import kotlinx.coroutines.flow.Flow

interface SubscriptionsRepository {

    val subscriptionState: Flow<SubscriptionState>

    fun purchase()

    fun reset()

}