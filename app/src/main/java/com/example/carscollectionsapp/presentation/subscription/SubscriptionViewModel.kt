package com.example.carscollectionsapp.presentation.subscription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carscollectionsapp.domain.SubscriptionsRepository
import com.example.carscollectionsapp.domain.entities.SubscriptionState
import com.example.carscollectionsapp.presentation.subscription.entities.SubscriptionScreenEffect
import com.example.carscollectionsapp.presentation.subscription.entities.SubscriptionScreenEvent
import com.example.carscollectionsapp.presentation.subscription.entities.SubscriptionScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val subscriptionsRepository: SubscriptionsRepository
) : ViewModel() {

    private val _effect = MutableSharedFlow<SubscriptionScreenEffect>()
    val effect = _effect.asSharedFlow()

    private val _state = MutableStateFlow<SubscriptionScreenState>(SubscriptionScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        load()
    }

    fun onEvent(event: SubscriptionScreenEvent) {
        when (event) {
            SubscriptionScreenEvent.OnPurchaseSubscriptionClicked -> onPurchaseSubscriptionClicked()
            SubscriptionScreenEvent.OnRejectPurchaseSubscriptionClicked -> onRejectPurchaseSubscriptionClicked()
        }
    }

    private fun onPurchaseSubscriptionClicked() = viewModelScope.launch {
        try {
            subscriptionsRepository.purchase()
            _effect.emit(SubscriptionScreenEffect.CloseDialogOnSuccessPurchasing)
        } catch (e: Exception) {
            _state.value = SubscriptionScreenState.Error(e)
        }
    }

    private fun onRejectPurchaseSubscriptionClicked() = viewModelScope.launch {
        _effect.emit(SubscriptionScreenEffect.CloseDialog)
    }


    private fun load() = viewModelScope.launch {
        try {
            val subscriptionState = subscriptionsRepository.subscriptionStateFlow.value
            if (subscriptionState is SubscriptionState.SubscribedState) {
                _effect.emit(SubscriptionScreenEffect.CloseDialogOnSuccessPurchasing)
            } else {
                val details = subscriptionsRepository.getSubscriptionDetails()
                _state.value = SubscriptionScreenState.Successful(details)
            }
        } catch (e: Exception) {
            _state.value = SubscriptionScreenState.Error(e)
        }
    }

}