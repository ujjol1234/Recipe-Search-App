package com.example.recipesearchapp.View

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


import androidx.navigation.NavController
import com.example.recipesearchapp.Screen

@Composable
fun HomeScreen(navController: NavController) {
    val tabTitles = listOf("All Recipes", "Popular Recipes","Favourites")
    val (selectedTabIndex, setSelectedTabIndex) = remember { mutableStateOf(0) }

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            tabs = {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { setSelectedTabIndex(index) },
                        text = { Text(title) }
                    )
                }
            }
        )
        when (selectedTabIndex) {
            0 -> AllRecipeScreen(onNavigatetoDetail = {
                navController.currentBackStackEntry?.savedStateHandle?.set("id", it)
                navController.navigate(Screen.RecipeDetailScreen.route)
            })
            1 -> PopularRecipeScreen(onNavigatetoDetail = {
                navController.currentBackStackEntry?.savedStateHandle?.set("id", it)
                navController.navigate(Screen.RecipeDetailScreen.route)
            })

            2-> Favourites(onNavigatetoDetail = {
                navController.currentBackStackEntry?.savedStateHandle?.set("id", it)
                navController.navigate(Screen.RecipeDetailScreen.route)
            })
        }
    }
}
