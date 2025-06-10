package com.example.catalist.ui.mappers
import com.example.catalist.breeds.domain.Breed
import com.example.catalist.ui.model.BreedUiModel

fun Breed.toUiModel(): BreedUiModel {
    return BreedUiModel(
        id = id,
        name = name,
        altNames = altNames?.takeIf { it.isNotBlank() },
        shortDescription = if (description.length > 250) description.take(247) + "..." else description,
        temperament = temperament.split(", ").take(5)
    )
}