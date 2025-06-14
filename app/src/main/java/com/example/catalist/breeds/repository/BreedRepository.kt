package com.example.catalist.breeds.repository

import android.util.Log
import com.example.catalist.breeds.api.BreedsApi
import com.example.catalist.breeds.api.model.BreedApiModel
import com.example.catalist.breeds.api.model.toDomain
import com.example.catalist.breeds.domain.Breed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BreedRepository @Inject constructor(
    private val api: BreedsApi
) {

    suspend fun getAllBreeds(): List<BreedApiModel> {
        Log.d("Repository", "Pozivam API za sve rase...")
        return withContext(Dispatchers.IO) {
            api.getAllBreeds()
        }
    }
//        Log.d("Repository", "Pozivam API za sve rase...")
//        //api.getAllBreeds().map { it.toDomain() }
//        val result = api.getAllBreeds()
//        Log.d("Repository", "API vratio ${result.size} rasa")
//        return result
//    }

//    suspend fun searchBreeds(query: String): List<Breed> = withContext(Dispatchers.IO) {
//        api.searchBreeds(query).map { it.toDomain() }
//    }

    suspend fun getBreedById(id: String): BreedApiModel? {
        return withContext(Dispatchers.IO) {
            api.getBreedById(id)
        }
    }
}

