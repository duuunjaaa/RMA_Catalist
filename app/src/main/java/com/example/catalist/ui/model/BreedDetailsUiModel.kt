package com.example.catalist.ui.model

import com.example.catalist.breeds.api.model.ImageApiModel

data class BreedDetailsUiModel (
    val id: String,
    val name: String,
    val altNames: String?,
    val description: String,
    //val imageUrl: String,
    val originCountries: List<String>,
    val temperament: List<String>,
    val lifeSpan: String,
    val weight: String,             // npr. "3–5 kg"
    val traits: Map<String, Int>,   // ključ → vrednost, npr. "adaptability" -> 5
    val isRare: Boolean,
    val wikiUrl: String?,
    val imageId: String?,
    val adaptability: Int,
    val affectionLevel: Int,
    val childFriendly: Int,
    val dogFriendly: Int,
    val energyLevel: Int,
    val healthIssues: Int,
    val grooming: Int
)