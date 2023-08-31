package com.example.carscollectionsapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carscollectionsapp.domain.SubscriptionsRepository
import com.example.carscollectionsapp.presentation.settings.entities.SettingsScreenEffect
import com.example.carscollectionsapp.presentation.settings.entities.SettingsScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val subscriptionsRepository: SubscriptionsRepository
) : ViewModel() {

    private val _effect = MutableSharedFlow<SettingsScreenEffect>()
    val effect = _effect.asSharedFlow()
    fun onEvent(event: SettingsScreenEvent) {
        when (event) {
            SettingsScreenEvent.OnResetClicked -> {
                onResetClicked()
            }
        }
    }

    private fun onResetClicked() = viewModelScope.launch {
        subscriptionsRepository.reset()
        _effect.emit(SettingsScreenEffect.Reset)
    }

}