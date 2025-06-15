package com.example.catalist.ui.mappers
import com.example.catalist.breeds.domain.Breed
import com.example.catalist.ui.model.BreedDetailsUiModel
import com.example.catalist.ui.model.BreedUiModel

// todo ovo sad ne koristim
//fun Breed.toUiModel(): BreedUiModel {
//    return BreedUiModel(
//        id = id,
//        name = name,
//        altNames = altNames?.takeIf { it.isNotBlank() },
//        shortDescription = if (description.length > 250) description.take(247) + "..." else description,
//        temperament = temperament.split(", ").take(5)
//    )
//}
//
//fun Breed.toDetailsUiModel(): BreedDetailsUiModel {
//    return BreedDetailsUiModel(
//        id = id,
//        name = name,
//        altNames = altNames?.takeIf { it.isNotBlank() },
//        description = description,
//        temperament = temperament.split(", ").map { it.trim() },
//        originCountries = countries.split(", ").map { it.trim() },
//        lifeSpan = lifeSpan,
//        weight = "${
//            weight.metric.trim().replace(" - ", "–")
//        } kg",
//        traits = mapOf(
//            "Adaptability" to adaptability,
//            "Affection" to affectionLevel,
//            "Child Friendly" to childFriendly,
//            "Dog Friendly" to dogFriendly,
//            "Energy Level" to energyLevel,
//            "Grooming" to grooming,
//            "Health Issues" to healthIssues,
//            "Intelligence" to intelligence,
//            "Shedding" to sheddingLevel,
//            "Social Needs" to socialNeeds,
//            "Stranger Friendly" to strangerFriendly,
//            "Vocalisation" to vocalisation
//        ),
//        isRare = isRare == 1,
//        wikiUrl = wikipediaUrl?.takeIf { it.isNotBlank() },
//        image = TODO(),
//    )
//}