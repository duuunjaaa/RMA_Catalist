package com.example.catalist.breeds.api.model

import com.example.catalist.breeds.domain.Breed
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BreedApiModel(
    val id: String,
    val name: String,

    @SerialName("alt_names")
    val altNames: String? = null,

    val description: String,
    val temperament: String,

    @SerialName("origin")
    val origin: String,

    @SerialName("life_span")
    val lifeSpan: String,

    val weight: Weight,

    @SerialName("wikipedia_url")
    val wikipediaUrl: String? = null,

    val rare: Int = 0,

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

    val grooming: Int = 0,

    @SerialName("health_issues")
    val healthIssues: Int = 0,

    val intelligence: Int = 0,

    @SerialName("shedding_level")
    val sheddingLevel: Int = 0,

    @SerialName("social_needs")
    val socialNeeds: Int = 0,

    @SerialName("stranger_friendly")
    val strangerFriendly: Int = 0,

    val vocalisation: Int = 0,

    @SerialName("reference_image_id")
    val imageId: String? = null,
    //val image: ImageApiModel? = null // može biti null
)

//@Serializable
//data class ImageApiModel(
//    val id: String? = null,
//    val width: Int? = null,
//    val height: Int? = null,
//    val url: String? = null
//)

@Serializable
data class Weight(
    val metric: String
)

fun BreedApiModel.toDomain(): Breed {
    return Breed(
        id = id,
        name = name,
        altNames = altNames,
        description = description,
        temperament = temperament,
        countries = origin,
        lifeSpan = lifeSpan,
        weight = weight,
        wikipediaUrl = wikipediaUrl,
        isRare = rare,
        adaptability = adaptability,
        affectionLevel = affectionLevel,
        childFriendly = childFriendly,
        dogFriendly = dogFriendly,
        energyLevel = energyLevel,
        grooming = grooming,
        healthIssues = healthIssues,
        intelligence = intelligence,
        sheddingLevel = sheddingLevel,
        socialNeeds = socialNeeds,
        strangerFriendly = strangerFriendly,
        vocalisation = vocalisation
    )
}