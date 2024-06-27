package com.example.recipesearchapp.Model

data class SearchResponse(
    val results: List<Recipe>
)

data class Recipe(
    val id: Int,
    val title: String,
    val image: String
)
