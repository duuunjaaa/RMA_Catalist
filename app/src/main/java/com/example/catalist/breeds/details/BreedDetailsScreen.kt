package com.example.catalist.breeds.details

import android.content.Intent
import android.util.Log
import android.widget.RatingBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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

        RareBadge(breed.isRare)

        Text(text = breed.description, style = MaterialTheme.typography.bodyMedium)
        Text(text = "Origin: ${breed.originCountries.joinToString()}")
        Text(text = "Life span: ${breed.lifeSpan}")
        Text(text = "Weight: ${breed.weight}")
        Text(text = "Temperament: ${breed.temperament.joinToString()}")

        BreedTraitRating(label = "Adaptability", rating = breed.adaptability.toFloat())
        BreedTraitRating(label = "Affection Level", rating = breed.affectionLevel.toFloat())
        BreedTraitRating(label = "Child Friendly", rating = breed.childFriendly.toFloat())
        BreedTraitRating(label = "Dog Friendly", rating = breed.dogFriendly.toFloat())
        BreedTraitRating(label = "Energy Level", rating = breed.energyLevel.toFloat())
        BreedTraitRating(label = "Health Issues", rating = breed.healthIssues.toFloat())
        BreedTraitRating(label = "Grooming", rating = breed.grooming.toFloat())

//        RatingBar(text = "affectionLevel", rating = breed.affectionLevel.toFloat())
//        RatingBar(text = "childFriendly", rating = breed.childFriendly.toFloat())
//        RatingBar(text = "dogFriendly", rating = breed.dogFriendly.toFloat())
//        RatingBar(text = "energyLevel", rating = breed.energyLevel.toFloat())
//        RatingBar(text = "healthIssues", rating = breed.healthIssues.toFloat())
//        RatingBar(text = "grooming", rating = breed.grooming.toFloat())
    }
}
@Composable
fun RareBadge(isRare: Boolean) {
    val label = if (isRare) "Rare Breed" else "Common Breed"
    val icon = if (isRare) Icons.Default.Star else Icons.Default.Pets
    val color = if (isRare) Color(0xFFD4AF37) else MaterialTheme.colorScheme.primary

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .background(color.copy(alpha = 0.1f), shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            color = color,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


@Composable
private fun BreedTraitRating(
    label: String,
    rating: Float,
    maxStars: Int = 5
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "${rating.toInt()} / $maxStars",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        LinearProgressIndicator(
            progress = { rating / maxStars },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}


//@Composable
//private fun RatingBar(
//    text: String,
//    maxStars: Int = 5,
//    rating: Float,
//) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Text(text = "$text:")
//
//        Spacer(modifier = Modifier.weight(1f))
//
//        Row(
//            modifier = Modifier.selectableGroup(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            LinearProgressIndicator(
//                progress = {rating / maxStars.toFloat()}
//            )
//        }
//    }
//}