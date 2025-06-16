package com.example.catalist.breeds.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.catalist.core.compose.NoDataContent
import com.example.catalist.ui.model.BreedUiModel

@Composable
fun BreedsListScreen(
    viewModel: BreedsListViewModel,
    onBreedClick: (id: String) -> Unit,
) {
    val uiState = viewModel.state.collectAsState()

    BreedsListScreen(
        state = uiState.value,
        eventPublisher = { viewModel.setEvent(it) },
        onBreedClick = onBreedClick,
    )
}

@Composable
private fun BreedsListScreen(
    state: BreedsListContract.UiState,
    eventPublisher: (BreedsListContract.UiEvent) -> Unit,
    onBreedClick: (id: String) -> Unit,
) {

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error != null) {
            NoDataContent(
                text = "Error = ${state.error}",
            )
        }
        else if (state.breeds.isEmpty()) {
            NoDataContent(
                text = "There are no breeds.",
            )
        }else {
            MySearchBar(

                modifier = Modifier.fillMaxWidth(),
                onSearchTriggered = { query ->
                    eventPublisher(BreedsListContract.UiEvent.SearchBreeds(query))
                },
                onClearSearch = { eventPublisher(BreedsListContract.UiEvent.LoadBreeds)}
            )
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(state.breeds) { breed ->
                    BreedListItem(breed = breed, onClick = { onBreedClick(breed.id) })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(
    modifier: Modifier = Modifier,
    onSearchTriggered: (String) -> Unit,
    onClearSearch: () -> Unit
) {
    var query by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    SearchBar(
        query = query,
        onQueryChange = { query = it },
        onSearch = {
            if (query.isBlank()) {
                onClearSearch()
            } else {
                onSearchTriggered(query) // poziva ViewModel tek kad korisnik potvrdi pretragu
            }

        },
        active = active,
        onActiveChange = { active = it },
        placeholder = { Text("Search") },
        modifier = modifier
    ) {

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BreedListItem(breed: BreedUiModel, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = breed.name, style = MaterialTheme.typography.titleMedium)
            breed.altNames?.let {
                Text(text = "alternative name: $it", style = MaterialTheme.typography.bodySmall)
            }
            Text(text = breed.shortDescription, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(4.dp))


            FlowRow {
                breed.temperament.forEach {
                    AssistChip(
                        onClick = {},
                        label = { Text(it) },
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
            }
        }
    }
}
