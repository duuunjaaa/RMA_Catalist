package com.example.catalist.breeds.details

import com.example.catalist.breeds.domain.Breed
import com.example.catalist.ui.model.BreedUiModel

interface BreedsDetailsContract {
    data class UiState(
        val isLoading: Boolean = true,
        val breed: Breed, //todo: mozda mi treba neki novi UiModel .kt
        val error: String? = null,
    )

    sealed class UiEvent {
//        data object LoadBreeds : UiEvent()
//        data class SearchBreeds(val query: String) : UiEvent()
        // todo: vrv ovde ide za klik na wiki stranicu
    }

    sealed class SideEffect {
        //data class ShowToast(val message: String) : SideEffect()
    }
}