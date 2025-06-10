package com.example.catalist.breeds.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO: @Serializable se zuti??
@Serializable
data class Breed(
    val id: String,
    val name: String,

    @SerialName("alt_names")
    val altNames: String? = null,

    val description: String,
    val temperament: String,

    @SerialName("origin")
    val countries: String,

    @SerialName("life_span")
    val lifeSpan: String,

    val weight: Weight,

    @SerialName("wikipedia_url")
    val wikipediaUrl: String? = null,

    @SerialName("rare")
    val isRare: Int = 0,

    @SerialName("adaptability")
    val adaptability: Int = 0,

    @SerialName("affection_level")
    val affectionLevel: Int = 0,

    @SerialName("child_friendly")
    val childFriendly: Int = 0,

    @SerialName("dog_friendly")
    val dogFriendly: Int = 0,

    @SerialName("energy_level")
    val energyLevel: Int = 0,

    @SerialName("grooming")
    val grooming: Int = 0,

    @SerialName("health_issues")
    val healthIssues: Int = 0,

    @SerialName("intelligence")
    val intelligence: Int = 0,

    @SerialName("shedding_level")
    val sheddingLevel: Int = 0,

    @SerialName("social_needs")
    val socialNeeds: Int = 0,

    @SerialName("stranger_friendly")
    val strangerFriendly: Int = 0,

    @SerialName("vocalisation")
    val vocalisation: Int = 0
)

@Serializable
data class Weight(
    val metric: String // npr. "3 - 5"
)
