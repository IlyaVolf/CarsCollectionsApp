package com.example.carscollectionsapp.presentation.main_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carscollectionsapp.domain.CarsRepository
import com.example.carscollectionsapp.domain.SubscriptionsRepository
import com.example.carscollectionsapp.domain.entities.SubscriptionState
import com.example.carscollectionsapp.presentation.main_screen.entities.MainScreenEffect
import com.example.carscollectionsapp.presentation.main_screen.entities.MainScreenEvent
import com.example.carscollectionsapp.presentation.main_screen.entities.MainScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val carsRepository: CarsRepository,
    private val subscriptionsRepository: SubscriptionsRepository
) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    private val _effect = MutableSharedFlow<MainScreenEffect>()
    val effect = _effect.asSharedFlow()

    private val _state = MutableStateFlow<MainScreenState>(MainScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            searchQuery.collectLatest { queryText ->
                load(queryText)
            }
        }
    }

    private suspend fun load(queryText: String) {
        try {
            _state.value = MainScreenState.Loading

            carsRepository.getCars(queryText).collectLatest { cars ->
                _state.value = MainScreenState.Successful(cars)
            }
        } catch (e: Exception) {
            _state.value = MainScreenState.Error
        }
    }

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.OnAddNewCarClicked -> onAddNewCarClicked()

            is MainScreenEvent.OnCarClicked -> onCarClicked(event.id)

            is MainScreenEvent.OnSettingsClicked -> onSettingsClicked()
        }
    }

    private fun onAddNewCarClicked() = viewModelScope.launch {
        when (val subscriptionState = subscriptionsRepository.subscriptionStateFlow.value) {
            is SubscriptionState.SubscribedState -> {
                subscriptionsRepository.countAsCarAddOpened()
                _effect.emit(MainScreenEffect.NavigateToCarAddScreen)
            }

            is SubscriptionState.UnsubscribedState -> {
                if (subscriptionState.carUploadCount > 0) {
                    subscriptionsRepository.countAsCarAddOpened()
                    _effect.emit(MainScreenEffect.NavigateToCarAddScreen)
                } else {
                    _effect.emit(MainScreenEffect.OpenSubscriptionPopUpScreen)
                }
            }
        }
    }

    private fun onCarClicked(id: Long) = viewModelScope.launch {
        when (val subscriptionState = subscriptionsRepository.subscriptionStateFlow.value) {
            is SubscriptionState.SubscribedState -> {
                subscriptionsRepository.countAsCarDetailsOpened()
                _effect.emit(MainScreenEffect.NavigateToCarDetailsScreen(id))
            }

            is SubscriptionState.UnsubscribedState -> {
                if (subscriptionState.carWatchCount > 0) {
                    subscriptionsRepository.countAsCarDetailsOpened()
                    _effect.emit(MainScreenEffect.NavigateToCarDetailsScreen(id))
                } else {
                    _effect.emit(MainScreenEffect.OpenSubscriptionPopUpScreen)
                }
            }
        }
    }

    private fun onSettingsClicked() = viewModelScope.launch {
        _effect.emit(MainScreenEffect.NavigateToSettingsScreen)
    }


}