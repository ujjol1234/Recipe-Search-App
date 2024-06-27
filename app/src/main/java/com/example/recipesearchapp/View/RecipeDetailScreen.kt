package com.example.recipesearchapp.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipesearchapp.ViewModel.RecipeDetailViewModel
import com.example.recipesearchapp.ViewModel.SimillarRecipeViewModel

@Composable
fun RecipeDetail(id:Int){
    val RecipeDetailViewModel: RecipeDetailViewModel = viewModel()
    val SimillarRecipeViewModel: SimillarRecipeViewModel = viewModel()
    RecipeDetailViewModel.fetchcategories("${id}")
    SimillarRecipeViewModel.fetchcategories("${id}")
    val detailstate by RecipeDetailViewModel.categoryState
    val similarstate by SimillarRecipeViewModel.categoryState
    var dataavailable by mutableStateOf(false)
    LazyColumn(Modifier.fillMaxSize()){
        item {
            when {
                detailstate.loading  && similarstate.loading-> {
                    Text(text = "Loading", fontWeight = FontWeight.Bold)
                }

                detailstate.Error != null -> {
                    Text(text = "Error Occured! ${detailstate.Error}")
                }
                
                similarstate.Error != null -> {
                    Text(text = "Error Occured! ${similarstate.Error}")
                }

                else -> {
                    dataavailable = true
                }


            }
        }
        if(dataavailable) {
            item {
                Text(text = "Nutrients", fontWeight = FontWeight.Bold, fontSize = 25.sp)
            }
            items(detailstate.recipedetail.nutrition.nutrients){
                    Row (Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = it.name, fontWeight = FontWeight.Bold)
                        Text(text = "${it.amount} ${it.unit}  ",fontWeight = FontWeight.Bold)
                    }
            }
        }

        if(dataavailable) {
            item {
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
                Text(text = "Ingridients", fontWeight = FontWeight.Bold, fontSize = 25.sp)
            }
            items(detailstate.recipedetail.extendedIngredients){
                    Row (Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = "${it.name}", fontWeight = FontWeight.Bold)
                        Text(text = "${it.amount} ${it.unit} ", fontWeight = FontWeight.Bold)
                    }
            }

        }

        if(dataavailable) {
            item {
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
                Text(text = "Instructions", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                Text(text = detailstate.recipedetail.instructions, fontWeight = FontWeight.Bold)
            }
        }
        if(dataavailable){
            item{
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
                Text(text = "Similar Recipes", fontWeight = FontWeight.Bold, fontSize = 25.sp)
            }
            items(similarstate.recipedetail){
                Row (Modifier.fillMaxWidth()){
                    Text(text = "${it.title} | COOKING TIME: ${it.readyInMinutes} Mins", fontWeight = FontWeight.Bold)
                }
            }
        }


    }
}