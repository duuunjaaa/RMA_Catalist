package com.example.catalist.breeds.api

import com.example.catalist.breeds.api.model.BreedApiModel
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BreedsApi {

    @GET("breeds")
    suspend fun getAllBreeds(): List<BreedApiModel>

    @GET("breeds/{id}")
    suspend fun getBreedById(@Path("id") id: String): BreedApiModel

    @GET("breeds/search")
    suspend fun searchBreeds(@Query("q") query: String): List<BreedApiModel>

    @GET("images/search")
    suspend fun getImageForBreed(@Query("breed_ids") breedId: String): List<ImageResponse>
}

@Serializable
data class ImageResponse(
    val url: String
)