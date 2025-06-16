package com.example.catalist.breeds.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalist.breeds.api.model.BreedApiModel
import com.example.catalist.breeds.list.BreedsListContract.*
import com.example.catalist.breeds.repository.BreedRepository
//import com.example.catalist.ui.mappers.toUiModel
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

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    private fun setState(reducer: UiState.() -> UiState) = _state.getAndUpdate(reducer)

    private val events = MutableSharedFlow<UiEvent>()
    fun setEvent(event: UiEvent) = viewModelScope.launch { events.emit(event) }

    init {
        fetchBreeds()
        observeEvents()
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect { event ->
                when (event) {
                    is UiEvent.LoadBreeds -> fetchBreeds()
                    is UiEvent.SearchBreeds -> searchBreeds(event.query)
                }
            }
        }
    }

//    fun onEvent(event: UiEvent) {
//        when (event) {
//            is UiEvent.LoadBreeds -> fetchBreeds()
//            is UiEvent.SearchQueryChanged -> searchBreeds(event.query)
//            //is BreedsListContract.UiEvent.SearchBreeds -> searchBreeds(event.query)
//            //is BreedsListContract.UiEvent.SearchBreeds ->
//        }
//    }

    private fun fetchBreeds() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            runCatching {
                repository.getAllBreeds()
            }.onSuccess { breeds ->
                _state.value = UiState(
                    isLoading = false,
                    breeds = breeds.map { it.asBreedUiModel() }
                )
            }.onFailure { error ->
                _state.value = _state.value.copy(
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
//        viewModelScope.launch {
//            setCatsState {
//                copy(
//                    catsFiltered =
//                        if (query.isBlank())
//                            cats
//                        else
//                            cats.filter { catInfoDetails -> catInfoDetails.doesMatchSearchQuery(query) },
//                    searchText = query
//                )
//            }
//        }
//    }

    private fun searchBreeds(query: String) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            runCatching {
                repository.searchBreeds(query)
            }.onSuccess { breeds ->
                _state.value = UiState(
                    isLoading = false,
                    breeds = breeds.map { it.asBreedUiModel() },
                    //searchText = query
                )
            }.onFailure { error ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = error.message
                )
            }
        }
    }
}
