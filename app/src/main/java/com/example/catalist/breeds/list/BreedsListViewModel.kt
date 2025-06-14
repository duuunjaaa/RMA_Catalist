package com.example.catalist.breeds.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalist.breeds.api.model.BreedApiModel
import com.example.catalist.breeds.repository.BreedRepository
import com.example.catalist.ui.mappers.toUiModel
import com.example.catalist.ui.model.BreedUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.String

@HiltViewModel
class BreedsListViewModel @Inject constructor(
    private val repository: BreedRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BreedsListContract.UiState())
    val uiState: StateFlow<BreedsListContract.UiState> = _uiState.asStateFlow()

    init {
        fetchBreeds() // sad dodala
    }

    fun onEvent(event: BreedsListContract.UiEvent) {
        when (event) {
            is BreedsListContract.UiEvent.LoadBreeds -> fetchBreeds()
            //is BreedsListContract.UiEvent.SearchBreeds -> searchBreeds(event.query)
            is BreedsListContract.UiEvent.SearchBreeds -> TODO()
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
                    breeds = breeds.map { it.asBreedUiModel() }
                )
            }.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = error.message
                )
            }
        }
    }

    private fun BreedApiModel.asBreedUiModel() = BreedUiModel(
        id = this.id,
        name = this.name,
        altNames = this.altNames?.takeIf { it.isNotBlank() },
        shortDescription = if (this.description.length > 250) this.description.take(247) + "..." else this.description,
        temperament = this.temperament.split(", ").take(5)
    )

//    private fun searchBreeds(query: String) {
//        _uiState.value = _uiState.value.copy(isLoading = true)
//        viewModelScope.launch {
//            runCatching {
//                repository.searchBreeds(query)
//            }.onSuccess { breeds ->
//                _uiState.value = BreedsListContract.UiState(
//                    isLoading = false,
//                    breeds = breeds.map { it.toUiModel() }
//                )
//            }.onFailure { error ->
//                _uiState.value = _uiState.value.copy(
//                    isLoading = false,
//                    error = error.message
//                )
//            }
//        }
//    }
}
