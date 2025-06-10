package com.example.catalist.breeds.list

import com.example.catalist.ui.model.BreedUiModel

interface BreedsListContract {

    data class UiState(
        val isLoading: Boolean = true,
        val breeds: List<BreedUiModel> = emptyList(),
        val error: String? = null,
    )

    sealed class UiEvent {
        data object LoadBreeds : UiEvent()
        data class SearchBreeds(val query: String) : UiEvent()
    }

    sealed class SideEffect {
        data class ShowToast(val message: String) : SideEffect()
    }
}