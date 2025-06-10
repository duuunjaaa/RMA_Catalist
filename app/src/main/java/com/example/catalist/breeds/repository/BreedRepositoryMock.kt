package com.example.catalist.breeds.repository

import com.example.catalist.breeds.domain.Breed
import com.example.catalist.breeds.domain.Weight
import javax.inject.Inject

class BreedRepositoryMock@Inject constructor(

): BreedsRepositoryInterface {

    override suspend fun getAllBreeds(): List<Breed> {
        return listOf(
            Breed(
                id = "abys",
                name = "Abyssinian",
                altNames = "Abys",
                description = "The Abyssinian is a playful and intelligent breed.",
                temperament = "Active, Energetic, Independent, Intelligent, Gentle",
                countries = "Egypt",
                lifeSpan = "14 - 15",
                weight = Weight("3 - 5"),
                wikipediaUrl = "https://en.wikipedia.org/wiki/Abyssinian_(cat)",
                isRare = 0,
                adaptability = 5,
                affectionLevel = 5,
                childFriendly = 4,
                dogFriendly = 5,
                energyLevel = 5,
                grooming = 1,
                healthIssues = 2,
                intelligence = 5,
                sheddingLevel = 3,
                socialNeeds = 4,
                strangerFriendly = 5,
                vocalisation = 2
            ),
            Breed(
                id = "beng",
                name = "Bengal",
                altNames = null,
                description = "Bengals are very active and playful cats.",
                temperament = "Alert, Agile, Energetic, Demanding, Intelligent",
                countries = "United States",
                lifeSpan = "12 - 16",
                weight = Weight("4 - 7"),
                wikipediaUrl = "https://en.wikipedia.org/wiki/Bengal_(cat)",
                isRare = 0,
                adaptability = 4,
                affectionLevel = 4,
                childFriendly = 4,
                dogFriendly = 5,
                energyLevel = 5,
                grooming = 1,
                healthIssues = 3,
                intelligence = 5,
                sheddingLevel = 2,
                socialNeeds = 5,
                strangerFriendly = 5,
                vocalisation = 3
            )
        )
    }

    override suspend fun searchBreeds(query: String): List<Breed> {
        return getAllBreeds().filter { it.name.contains(query, ignoreCase = true) }
    }
}