package com.example.catalist.ui.model

data class BreedUiModel(
    val id: String,
    val name: String,
    val altNames: String?,
    val shortDescription: String,
    val temperament: List<String>
)