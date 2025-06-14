package com.example.catalist.ui.model

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
    val wikiUrl: String?
)