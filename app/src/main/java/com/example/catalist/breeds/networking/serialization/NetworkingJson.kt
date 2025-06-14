package com.example.catalist.breeds.networking.serialization

import kotlinx.serialization.json.Json

val NetworkingJson = Json {
    ignoreUnknownKeys = true
    prettyPrint = true
}
