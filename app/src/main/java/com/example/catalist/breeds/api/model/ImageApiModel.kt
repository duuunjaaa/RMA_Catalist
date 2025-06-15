package com.example.catalist.breeds.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageApiModel (
    val id: String? = null,
    val width: Int? = null,
    val height: Int? = null,
    val url: String? = null
)