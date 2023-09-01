package com.example.carscollectionsapp.domain

import com.example.carscollectionsapp.domain.entities.SubscriptionState
import kotlinx.coroutines.flow.Flow

interface SubscriptionsRepository {

    fun getSubscriptionStateFlow(): Flow<SubscriptionState>

    fun purchase()

    fun reset()

}