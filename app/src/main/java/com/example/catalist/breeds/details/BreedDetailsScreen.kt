package com.example.catalist.breeds.details

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.catalist.core.compose.LoadingIndicator
import androidx.core.net.toUri
import coil3.compose.SubcomposeAsyncImage
import com.example.catalist.breeds.list.BreedsListContract
import com.example.catalist.ui.model.BreedDetailsUiModel


//todo: ovde me zeza - jel mi treba uopste eventPublisher i\ili onWikiClick,
// ili mi je dovoljno samo da 'opalim Intent' i  da se otvori wiki url??
@Composable
fun BreedDetailsScreen(
    viewModel: BreedDetailsViewModel = hiltViewModel(),
    //onWikiClick: () -> Unit,
    onClose: () -> Unit
){
    val uiState = viewModel.state.collectAsState()

    BreedDetailsScreen(
        state = uiState.value,
        //eventPublisher = { viewModel.setEvent(it) },
        //onWikiClick = onWikiClick,
        onClose = onClose,
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BreedDetailsScreen(
    state: BreedDetailsContract.UiState,
    //eventPublisher: (BreedDetailsContract.UiEvent) -> Unit, // todo: eventPublisher mi ne radi nista ovde? mzd mi ne treba za ovaj ekran?
    //onWikiClick: () -> Unit, // todo: videti imam li argumente za WikiClick
    onClose: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.breed?.name ?: "Breed details") },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
//
//                actions = {
//                    if (state.breed?.wikiUrl != null) {
//                        IconButton(onClick = {onWikiClick()}) {
//                            Icon(Icons.Default.Info, contentDescription = "Wikipedia")
//                        }
//                    }
//                }
            )
        }
    ) { padding ->
        when {
            state.isLoading -> {
                LoadingIndicator(modifier = Modifier.padding(padding))
            }
            state.breed != null -> {

                Column(
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    Log.d("test BREED IMAGE URL = ", state.breed.imageId.toString())
                    SubcomposeAsyncImage(
                        //modifier = Modifier.size(100.dp),
                        modifier = Modifier.fillMaxWidth(),
                        //model = state.breed.image?.url,
                        //model = "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg",
                        model = ("https://cdn2.thecatapi.com/images/" + state.breed.imageId + ".jpg")
                            ?: "",
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
//                                contentScale = ContentScale.Fit,
//                                contentScale = ContentScale.FillWidth,
//                                contentScale = ContentScale.Crop,
                        loading = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(36.dp),
                                )
                            }
                        },
                        error = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Image(
                                    imageVector = Icons.Default.Error,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                    BreedDetailsContent(
                        breed = state.breed,
//                        modifier = Modifier
//                            .padding(padding)
//                            .padding(16.dp)
                    )
                    val context = LocalContext.current
                    Button(
                        onClick = {
                            state.breed.wikiUrl?.let { url ->
                                context.startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        url.toUri()
                                    )
                                )
                            }
                        }
                    ) {
                        Text(text = "Wiki")
                    }
                }
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
private fun BreedDetailsContent(
    breed: BreedDetailsUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = breed.name, style = MaterialTheme.typography.headlineSmall)
       breed.altNames?.let {
            Text(text = "aka: $it", style = MaterialTheme.typography.bodySmall)
        }
        Text(text = breed.description, style = MaterialTheme.typography.bodyMedium)
        Text(text = "Origin: ${breed.originCountries.joinToString()}")
        Text(text = "Life span: ${breed.lifeSpan}")
        Text(text = "Weight: ${breed.weight}")
        Text(text = "Temperament: ${breed.temperament.joinToString()}")
    }
}