package com.example.catalist.breeds.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalist.breeds.api.model.BreedApiModel
import com.example.catalist.breeds.details.BreedDetailsContract.*
import com.example.catalist.breeds.repository.BreedRepository
import com.example.catalist.navigation.breedId
import com.example.catalist.navigation.breedIdOrThrow
import com.example.catalist.ui.model.BreedDetailsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.String

@HiltViewModel
class BreedDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: BreedRepository
) : ViewModel() {

    private val breedId: String = savedStateHandle.breedIdOrThrow

    private val _state = MutableStateFlow(UiState(breedId = breedId))
    val state = _state.asStateFlow()
    private fun setState(reducer: UiState.() -> UiState) = _state.getAndUpdate(reducer)

    private val events = MutableSharedFlow<UiEvent>()
    fun setEvent(event: UiEvent) = viewModelScope.launch {
        events.emit(event)
    }

    private val _sideEffect: Channel<SideEffect> = Channel()
    val sideEffect = _sideEffect.receiveAsFlow()
    private fun setSideEffect(effect: SideEffect) = viewModelScope.launch { _sideEffect.send(effect) }


    init {
        observeEvents()
        loadBreed()
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect { event ->
                when (event) {
                    UiEvent.OpenWikiPage -> openWikiPage()
                }
            }
        }
    }
    private fun openWikiPage() = viewModelScope.launch {
 //       state.value.breed?.wikiUrl?.let {_sideEffect.emit(SideEffect.OpenUrl(it))
//        passwordRepository.removePassword(passwordId)
//        setEffect(SideEffect.PasswordDeleted)
    }

    // moja stara observeEvents fja
//    private fun observeEvents() = viewModelScope.launch {
//        events.collect { event ->
//            when (event) {
//                is UiEvent.OpenWikiPage -> {
//                    state.value.breed?.wikiUrl?.let {
//                        _sideEffect.emit(SideEffect.OpenUrl(it))
//                    }
//                }
//            }
//        }
//    }

    private fun loadBreed() = viewModelScope.launch {
        setState { copy(isLoading = true) }

        runCatching {
            repository.getBreedById(breedId)
        }.onSuccess { breed ->
            if (breed != null) {
                setState {
                    copy(
                        isLoading = false,
                        breed = breed.asBreedDetailsUiModel()
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
    private fun BreedApiModel.asBreedDetailsUiModel() = BreedDetailsUiModel(
        id = this.id,
        name = this.name,
        altNames = altNames?.takeIf { it.isNotBlank() },
        description = description,
        temperament = temperament.split(", ").map { it.trim() },
        originCountries = this.origin.split(", ").map { it.trim() },
        lifeSpan = lifeSpan,
        weight = "${
            weight.metric.trim().replace(" - ", "–")
        } kg",
        traits = mapOf(
            "Adaptability" to adaptability,
            "Affection" to affectionLevel,
            "Child Friendly" to childFriendly,
            "Dog Friendly" to dogFriendly,
            "Energy Level" to energyLevel,
            "Grooming" to grooming,
            "Health Issues" to healthIssues,
            "Intelligence" to intelligence,
            "Shedding" to sheddingLevel,
            "Social Needs" to socialNeeds,
            "Stranger Friendly" to strangerFriendly,
            "Vocalisation" to vocalisation
        ),
        isRare = this.rare == 1,
        wikiUrl = wikipediaUrl?.takeIf { it.isNotBlank() },
        imageId = this.imageId
    )
}