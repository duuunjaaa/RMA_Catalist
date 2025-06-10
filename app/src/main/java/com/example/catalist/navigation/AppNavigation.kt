package com.example.catalist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.catalist.breeds.list.BreedsListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "breeds"
    ) {
        composable("breeds") {
            BreedsListScreen(onBreedClick = { breedId ->
                // kasnije možeš da dodaš: navController.navigate("details/$breedId")
            })
        }
    }
}
