package com.example.catalist.navigation

import android.R.attr.type
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.catalist.breeds.details.BreedsDetailsScreen
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
                navController.navigate("breeds/details/$breedId")
            })
        }
        composable(
            route = "breeds/details/{breedId}",
            arguments = listOf(navArgument("breedId") {
                type = NavType.StringType
            })
        ) {
            BreedsDetailsScreen(
                onClose = { navController.popBackStack() }
            )
        }
    }
}
