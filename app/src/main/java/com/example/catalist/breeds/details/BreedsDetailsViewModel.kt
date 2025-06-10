package com.example.catalist.breeds.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalist.breeds.repository.BreedsRepositoryInterface
import com.example.catalist.ui.mappers.toDetailsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: BreedsRepositoryInterface
) : ViewModel() {

    private val breedId: String = checkNotNull(savedStateHandle["breedId"])
    private val _state = MutableStateFlow(BreedsDetailsContract.UiState(breedId = breedId))
    val state = _state.asStateFlow()
    private fun setState(reducer: BreedsDetailsContract.UiState.() -> BreedsDetailsContract.UiState) =
        _state.update(reducer)

    private val _sideEffect = MutableSharedFlow<BreedsDetailsContract.SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private val events = MutableSharedFlow<BreedsDetailsContract.UiEvent>()
    fun setEvent(event: BreedsDetailsContract.UiEvent) = viewModelScope.launch {
        events.emit(event)
    }

    init {
        observeEvents()
        loadBreed()
    }

    private fun observeEvents() = viewModelScope.launch {
        events.collect { event ->
            when (event) {
                is BreedsDetailsContract.UiEvent.OpenWikiPage -> {
                    state.value.breed?.wikiUrl?.let {
                        _sideEffect.emit(BreedsDetailsContract.SideEffect.OpenUrl(it))
                    }
                }
            }
        }
    }

    private fun loadBreed() = viewModelScope.launch {
        setState { copy(isLoading = true) }
        setState { copy(isLoading = true) }

        runCatching {
            repository.getBreedById(breedId)
        }.onSuccess { breed ->
            if (breed != null) {
                setState {
                    copy(
                        isLoading = false,
                        breed = breed.toDetailsUiModel()
                    )
                }
            } else {
                setState {
                    copy(
                        isLoading = false,
                        error = "Breed not found."
                    )
                }
            }
        }.onFailure {
            setState {
                copy(
                    isLoading = false,
                    error = it.message ?: "Error when loading breed."
                )
            }
        }
    }
}