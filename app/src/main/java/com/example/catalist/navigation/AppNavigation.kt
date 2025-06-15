package com.example.catalist.navigation

import android.R.attr.type
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.catalist.breeds.details.BreedDetailsViewModel
import com.example.catalist.breeds.details.BreedDetailsScreen
import com.example.catalist.breeds.list.BreedsListScreen
import com.example.catalist.breeds.list.BreedsListViewModel


private fun NavController.navigateToDetails(breedId: String) {
    this.navigate(route = "details/$breedId")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list"
    ){
        breedsList(route = "list", navController)
        breedDetails(
            route = "details/{$BREED_ID_ARG}",
            arguments = listOf(
                navArgument(name = BREED_ID_ARG) {
                    type = NavType.StringType
                    nullable = false
                }
            ),
            navController = navController
        )

    }
}

private fun NavGraphBuilder.breedsList(
    route: String,
    navController: NavController
) = composable(route = route) {
    val viewModel= hiltViewModel<BreedsListViewModel>()

    BreedsListScreen(
        viewModel,
        onBreedClick = {breedId -> navController.navigateToDetails(breedId = breedId)}
    )
}

private fun NavGraphBuilder.breedDetails(
    route: String,
    arguments: List<NamedNavArgument>,
    navController: NavController,
) = composable(route = route, arguments = arguments) { navBackStackEntry ->
    val viewModel = hiltViewModel< BreedDetailsViewModel>()
    Log.d("test", viewModel.toString())
    BreedDetailsScreen(
        viewModel = viewModel,
        //onWikiClick = () -> navController.navigateToWikiPage(wikipediaUrl),
        onClose = {
            navController.navigateUp()
        }
    )
}


//@Composable
//fun AppNavigation() {
//    val navController = rememberNavController()
//
//    NavHost(
//        navController = navController,
//        startDestination = "breeds"
//    ) {
//        composable("breeds") {
//            BreedsListScreen(onBreedClick = { breedId ->
//                navController.navigate("breeds/details/$breedId")
//            })
//        }
//        composable(
//            route = "breeds/details/{breedId}",
//            arguments = listOf(navArgument("breedId") {
//                type = NavType.StringType
//            })
//        ) {
//            BreedsDetailsScreen(
//                onClose = { navController.popBackStack() }
//            )
//        }
//    }

