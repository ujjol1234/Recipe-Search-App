package com.example.recipesearchapp.Model


data class RecipeDetailResponse(
    val extendedIngredients: List<ExtendedIngredient>,
    val nutrition: nutrition,
    val instructions:String
)

data class nutrition(
    val nutrients:List<nutrients>
)

data class nutrients (
    val name:String,
    val amount:Float,
    val unit: String
)