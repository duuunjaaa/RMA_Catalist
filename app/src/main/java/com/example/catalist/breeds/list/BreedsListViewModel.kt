package com.example.catalist.breeds.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalist.breeds.repository.BreedsRepositoryInterface
import com.example.catalist.ui.mappers.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedsListViewModel @Inject constructor(
    private val repository: BreedsRepositoryInterface
) : ViewModel() {

    private val _uiState = MutableStateFlow(BreedsListContract.UiState())
    val uiState: StateFlow<BreedsListContract.UiState> = _uiState.asStateFlow()

    fun onEvent(event: BreedsListContract.UiEvent) {
        when (event) {
            is BreedsListContract.UiEvent.LoadBreeds -> fetchBreeds()
            is BreedsListContract.UiEvent.SearchBreeds -> searchBreeds(event.query)
        }
    }

    private fun fetchBreeds() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            runCatching {
                repository.getAllBreeds()
            }.onSuccess { breeds ->
                _uiState.value = BreedsListContract.UiState(
                    isLoading = false,
                    breeds = breeds.map { it.toUiModel() }
                )
            }.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = error.message
                )
            }
        }
    }

    private fun searchBreeds(query: String) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            runCatching {
                repository.searchBreeds(query)
            }.onSuccess { breeds ->
                _uiState.value = BreedsListContract.UiState(
                    isLoading = false,
                    breeds = breeds.map { it.toUiModel() }
                )
            }.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = error.message
                )
            }
        }
    }
}
