package com.example.recipesearchapp.Model

data class RecipeResponse(
    val recipes: List<recipes>
)

data class recipes(
    val extendedIngredients: List<ExtendedIngredient>,
    val id: Int,
    val title: String,
    val image: String,
    val instructions: String,
    val analyzedInstructions: List<AnalyzedInstruction>
)

data class ExtendedIngredient(
    val name: String,
    val amount: Double,
    val unit: String
)

data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)

data class Step(
    val number: Int,
   val step: String,
)
