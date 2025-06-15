package com.example.catalist.breeds.repository

import android.util.Log
import com.example.catalist.breeds.api.BreedsApi
import com.example.catalist.breeds.api.model.BreedApiModel
import com.example.catalist.breeds.api.model.ImageApiModel
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

    suspend fun searchBreeds(query: String): List<BreedApiModel> {
        return withContext(Dispatchers.IO) {
            api.searchBreeds(query)
        }
    }

    suspend fun getBreedById(id: String): BreedApiModel? {
        return withContext(Dispatchers.IO) {
            api.getBreedById(id)
        }
    }
//suspend fun getBreedById(id: String): BreedApiModel? {
//    return withContext(Dispatchers.IO) {
//        val breed = api.getBreedById(id)
//
//        // Ako postoji reference_image_id, pokušaj da uzmeš sliku
//        val imageUrl = breed.imageId?.let { refId ->
//            try {
//                api.getImageById(refId).url
//            } catch (e: Exception) {
//                Log.w("Repository", "Nije moguće dobiti sliku za $refId: ${e.message}")
//                null
//            }
//        }
//
//        // Vrati novi breed objekat sa 'image' postavljenim ručno
//        val imageModel: ImageApiModel? = imageUrl?.let { ImageApiModel(url = it) }
//        breed.copy(image = imageModel)
//    }
//}

}

