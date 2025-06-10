package com.example.catalist.breeds.details

import com.example.catalist.breeds.domain.Breed
import com.example.catalist.ui.model.BreedDetailsUiModel
import com.example.catalist.ui.model.BreedUiModel

interface BreedsDetailsContract {
    data class UiState(
        val isLoading: Boolean = true,
        val breedId: String,
        val breed: BreedDetailsUiModel? = null,
        val error: String? = null
    )

    sealed class UiEvent {
        data object OpenWikiPage : UiEvent()
    }

    sealed class SideEffect {
        data class OpenUrl(val url: String) : SideEffect()
    }
}