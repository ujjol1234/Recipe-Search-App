package com.example.recipesearchapp.View

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.recipesearchapp.Model.Recipe
import com.example.recipesearchapp.ViewModel.RecipeDetailViewModel
import com.example.recipesearchapp.ViewModel.SearchViewModel
import com.example.recipesearchapp.ViewModel.SimillarRecipeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {
    val SearchViewModel: SearchViewModel = viewModel()
    val SimillarRecipeViewModel: SimillarRecipeViewModel = viewModel()
    val RecipeDetailViewModel: RecipeDetailViewModel = viewModel()
    val detailstate by SearchViewModel.categoryState
    val recipedetailstate by RecipeDetailViewModel.categoryState
    val similarstate by SimillarRecipeViewModel.categoryState
    var query by remember { mutableStateOf("") }
    var cuisine by remember { mutableStateOf("") }
    var excludeIngredients by remember { mutableStateOf("") }
    var minProtein by remember { mutableStateOf("") }
    var maxCarbs by remember { mutableStateOf("") }
    var maxCalories by remember { mutableStateOf("") }
    var isAdvancedSearch by remember { mutableStateOf(false) }
    val sheetState = androidx.compose.material3.rememberModalBottomSheetState()
    var issheetopen by rememberSaveable {
        mutableStateOf(false)
    }
    val sheetState2 = androidx.compose.material3.rememberModalBottomSheetState()
    var issheetopen2 by rememberSaveable {
        mutableStateOf(false)
    }
    val sheetState3 = androidx.compose.material3.rememberModalBottomSheetState()
    var issheetopen3 by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
            TextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth()
            )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isAdvancedSearch,
                onCheckedChange = { isAdvancedSearch = it }
            )
            Text(text = "Advanced Search")
        }

        if (isAdvancedSearch) {
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = cuisine,
                onValueChange = { cuisine = it },
                label = { Text("Cuisine") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = excludeIngredients,
                onValueChange = { excludeIngredients = it },
                label = { Text("Exclude Ingredients") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = minProtein,
                onValueChange = { minProtein = it },
                label = { Text("Min Protein") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = maxCarbs,
                onValueChange = { maxCarbs = it },
                label = { Text("Max Carbs") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = maxCalories,
                onValueChange = { maxCalories = it },
                label = { Text("Min Calories") },
                modifier = Modifier.fillMaxWidth()
            )

        }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        SearchViewModel.fetchcategories(
                            query,
                            cuisine,
                            excludeIngredients,
                            minProtein,
                            maxCarbs)
                    }
                ) {
                    Text("Search")
                }
            }
        Box (Modifier.fillMaxSize()) {
            when {
                detailstate.loading -> {
                    Text(text = "Loading")
                }

                detailstate.Error != null -> {
                    androidx.compose.material3.Text(text = "Error Occured! ${detailstate.Error}")
                }

                else -> {
                    SearchResultsList(results = detailstate.searchresults.results,{
                        RecipeDetailViewModel.fetchcategories(it.toString())
                        SimillarRecipeViewModel.fetchcategories(it.toString())
                        issheetopen=true})
                }
            }
        }
    }
    if(issheetopen) {
        ModalBottomSheet(
            onDismissRequest = { issheetopen=false },
            sheetState = sheetState,
        ) {
            Text(
                text = "Ingridients",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 27.sp,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.padding(vertical = 15.dp))
            recipedetailstate.recipedetail.extendedIngredients.forEach {
                Text(
                    text = "${it.name}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 24.dp))
            Row (Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center){
                Button(onClick = { issheetopen2=true }) {
                    Text(text = "Get Full Recipe")
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
    if(issheetopen2) {
        ModalBottomSheet(
            onDismissRequest = { issheetopen2=false },
            sheetState = sheetState2,
        ) {
            Text(
                text = "Recipe",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 27.sp,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.padding(vertical = 15.dp))
            Text(text = recipedetailstate.recipedetail.instructions,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                modifier = Modifier.padding(8.dp))
            Spacer(modifier = Modifier.padding(vertical = 24.dp))
            Row (Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center){
                Button(onClick = {issheetopen3=true
                }) {
                    Text(text = "Get Similar Recipe")
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
    if(issheetopen3) {
        ModalBottomSheet(
            onDismissRequest = { issheetopen3=false },
            sheetState = sheetState3,
        ) {
            Text(
                text = "Similar Recipes",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 27.sp,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.padding(vertical = 15.dp))

            similarstate.recipedetail.forEachIndexed { index, simillarRecipe ->
                Row (Modifier.fillMaxWidth()){
                        Text(text = "${index+1}  ",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            modifier = Modifier.padding(8.dp))

                        Text(text = "${simillarRecipe.title}   COOKING TIME: ${simillarRecipe.readyInMinutes} Mins",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            modifier = Modifier.padding(8.dp))
                }
            }

            Spacer(modifier = Modifier.padding(vertical = 24.dp))
        }
    }
    }

@Composable
fun SearchResultsList(results:List<Recipe>, onOpenDetail: (id:Int) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {
        items(results){
            SearchItem(cat = it,onOpenDetail)
        }
    }
}

@Composable
fun SearchItem(cat: Recipe, onOpenDetail:(id:Int)->Unit) {
    var isFavorite by remember { mutableStateOf(false) }
    val context = LocalContext.current
    if (Favourites.any { it.id == cat.id }) {
        isFavorite = true
    }
    Box(
        modifier = Modifier
            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))
            .size(300.dp)
            .padding(8.dp)
            .clickable { onOpenDetail(cat.id) }
    ) {Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {Image(
        painter = rememberAsyncImagePainter(model = cat.image),
        contentDescription = "Image",
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onOpenDetail(cat.id) }
    )
        Text(
            text = cat.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
        androidx.compose.material3.IconButton(
            onClick = {
                isFavorite = !isFavorite
                if (isFavorite) {
                    Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show()
                    Favourites.add(favourites(cat.id,cat.title,cat.image))
                }
                else{
                    Favourites.remove(favourites(cat.id,cat.title,cat.image))
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .size(40.dp)
                .background(Color.White, shape = CircleShape)
        ) {
            androidx.compose.material3.Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite Icon",
                tint = if (isFavorite) Color.Red else Color.Gray
            )
        }
    }

}