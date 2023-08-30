package com.example.carscollectionsapp.presentation.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carscollectionsapp.domain.CarsRepository
import com.example.carscollectionsapp.presentation.main_screen.entities.MainScreenEffect
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
    private val carsRepository: CarsRepository
) : ViewModel() {

    // todo аккуратно с ЖЦ флоу! Мб где-то использовать LD

    private val searchQuery = MutableStateFlow("")
    var searchQueryText: String
        get() = searchQuery.value
        set(value) {
            searchQuery.value = value
        }

    private val _effect = MutableSharedFlow<MainScreenEffect>()
    val effect = _effect.asSharedFlow()

    private val _state = MutableStateFlow<MainScreenState>(MainScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            load(searchQuery.value)
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


}