package com.example.catalist.breeds.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
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
                text = "There is no password.",
            )
        }else {
            TextField(
                value = state.searchText,
                onValueChange = { text ->
                    eventPublisher(BreedsListContract.UiEvent.SearchQueryChanged(query = text))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                placeholder = { Text(text = "Search") },
                shape = CircleShape,
                //leadingIcon = { AppIconButton(imageVector = Icons.Default.Search, onClick = { }) }
            )
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
