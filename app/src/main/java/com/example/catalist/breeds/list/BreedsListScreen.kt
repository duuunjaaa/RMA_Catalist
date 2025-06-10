package com.example.catalist.breeds.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.catalist.ui.model.BreedUiModel

@Composable
fun BreedsListScreen(
    viewModel: BreedsListViewModel = hiltViewModel(),
    onBreedClick: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(BreedsListContract.UiEvent.LoadBreeds)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error != null) {
            Text("Greška: ${state.error}", color = MaterialTheme.colorScheme.error)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(state.breeds) { breed ->
                    BreedListItem(breed = breed, onClick = { onBreedClick(breed.id) })
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BreedListItem(breed: BreedUiModel, onClick: () -> Unit) {
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
