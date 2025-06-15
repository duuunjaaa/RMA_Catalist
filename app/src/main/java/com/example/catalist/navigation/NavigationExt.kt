package com.example.catalist.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry

const val BREED_ID_ARG = "breedId"

// todo: ovde ide ovaj kod ali mi nije jos jasno cemu sluzi.
val NavBackStackEntry.breedId: String?
    get() = this.arguments?.getString(BREED_ID_ARG)

val NavBackStackEntry.breedIdOrThrow: String
    get() = this.arguments?.getString(BREED_ID_ARG)
        ?: error("$BREED_ID_ARG not found.")

val SavedStateHandle.breedId: String?
    get() = this.get<String>(BREED_ID_ARG)

val SavedStateHandle.breedIdOrThrow: String
    get() = this.get<String>(BREED_ID_ARG)
        ?: error("$BREED_ID_ARG not found.")
