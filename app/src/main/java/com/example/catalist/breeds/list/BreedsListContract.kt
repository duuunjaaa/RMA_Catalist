package com.example.catalist.breeds.list

import com.example.catalist.ui.model.BreedUiModel

interface BreedsListContract {

    data class UiState(
        val isLoading: Boolean = true,
        val breeds: List<BreedUiModel> = emptyList(),
        val breedsFiltered: List<BreedUiModel> = emptyList(),
        val isSearching: Boolean = false,
        val searchText: String = "",
        val error: String? = null,
    )

    sealed class UiEvent {
        data object LoadBreeds : UiEvent()
        data class SearchQueryChanged(val query: String) : UiEvent()
        //data class SearchBreeds(val query: String) : UiEvent()
    }

    sealed class SideEffect {
        data class ShowToast(val message: String) : SideEffect()
    }
}