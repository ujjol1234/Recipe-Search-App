package com.example.recipesearchapp.View

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.recipesearchapp.Model.result
import com.example.recipesearchapp.ui.theme.AllRecipeViewModel

@Composable
fun AllRecipeScreen(onNavigatetoDetail:(Int)->Unit){
    val AllRecipeViewModel:AllRecipeViewModel= viewModel()
    val recipeState by AllRecipeViewModel.categoryState
    Box (Modifier.fillMaxSize()){
        when{
            recipeState.loading ->{

            }
            recipeState.Error!=null->{
                Text(text = "Error Occured! ${recipeState.Error}")
            }
            else->{
                Categoryscreen(results = recipeState.list,onNavigatetoDetail)
            }


        }
    }
}

@Composable
fun Categoryscreen(results:List<result>, onNavigatetoDetail:(Int)->Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {
        items(results){
            CategoryItem(cat = it,onNavigatetoDetail)
        }
    }
}

@Composable
fun CategoryItem(cat: result, onNavigatetoDetail: (Int) -> Unit) {
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
            .clickable { onNavigatetoDetail(cat.id) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = cat.image),
                contentDescription = "Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clickable { onNavigatetoDetail(cat.id) }
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
        IconButton(
            onClick = {
                isFavorite = !isFavorite
                if (isFavorite) {
                    Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show()
                    Favourites.add(
                        favourites(id = cat.id,
                        image = cat.image,
                        title =  cat.title)
                    )
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
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite Icon",
                tint = if (isFavorite) Color.Red else Color.Gray
            )
        }
    }
}