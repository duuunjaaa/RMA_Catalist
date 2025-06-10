package com.example.catalist.breeds.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.catalist.core.compose.LoadingIndicator
import androidx.core.net.toUri
import com.example.catalist.ui.model.BreedDetailsUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedsDetailsScreen(
    onClose: () -> Unit,
    viewModel: BreedDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is BreedsDetailsContract.SideEffect.OpenUrl -> {
                    val intent = Intent(Intent.ACTION_VIEW, effect.url.toUri())
                    context.startActivity(intent)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.value.breed?.name ?: "Breed details") },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (state.value.breed?.wikiUrl != null) {
                        IconButton(onClick = {
                            viewModel.setEvent(BreedsDetailsContract.UiEvent.OpenWikiPage)
                        }) {
                            Icon(Icons.Default.Info, contentDescription = "Wikipedia")
                        }
                    }
                }
            )
        }
    ) { padding ->
        when {
            state.value.isLoading -> {
                LoadingIndicator(modifier = Modifier.padding(padding))
            }
            state.value.breed != null -> {
                BreedsDetailsContent(
                    breed = state.value.breed!!,
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp)
                )
            }
            else -> {
                Text(
                    "Error occurred.",
                    modifier = Modifier.padding(padding).padding(16.dp)
                )
            }
        }
    }
}

//todo: Ovo je samo osnova — treba jos i slika, temperamenti u FlowRow, osobine, dugme za wiki
@Composable
fun BreedsDetailsContent(
    breed: BreedDetailsUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = breed.name, style = MaterialTheme.typography.headlineSmall)
//       breed.altNames?.let {
//            Text(text = "aka: $it", style = MaterialTheme.typography.bodySmall)
//        }
        Text(text = breed.description, style = MaterialTheme.typography.bodyMedium)
        Text(text = "Poreklo: ${breed.originCountries.joinToString()}")
        Text(text = "Životni vek: ${breed.lifeSpan}")
        Text(text = "Težina: ${breed.weight}")
        Text(text = "Temperament: ${breed.temperament.joinToString()}")
        // todo: prevedi na eng ovo iznad
    }
}