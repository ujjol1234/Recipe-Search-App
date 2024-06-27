package com.example.recipesearchapp

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.recipesearchapp.View.HomeScreen
import com.example.recipesearchapp.View.RecipeDetail
import com.example.recipesearchapp.View.SearchScreen

open class Screen(val title:String, val route:String){
    object AllRecipeScreen:Screen("All Recipes","ARSCREEN")
    object PopularRecipeScreen:Screen("Popular Recipes","PRSCREEN")
    object HomeScreen:Screen("Home","Home")
    object RecipeDetailScreen:Screen("Recipe Detail","RD")
    object SearchScreen:Screen("Search Screen","search")
}

@Composable
fun Navigation(navController: NavController, pd: PaddingValues) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(Screen.RecipeDetailScreen.route) {
            val id = navController.previousBackStackEntry?.savedStateHandle?.get<Int>("id") ?: 0
            RecipeDetail(id = id)
        }
        composable(Screen.SearchScreen.route){
            SearchScreen()
        }
    }
}