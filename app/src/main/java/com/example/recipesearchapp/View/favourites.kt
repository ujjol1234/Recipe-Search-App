package com.example.recipesearchapp.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

data class favourites(
    val id: Int,
    val title: String,
    val image: String,
)

val Favourites = mutableStateListOf<favourites>()

@Composable
fun Favourites(onNavigatetoDetail: (Int) -> Unit){
    if(Favourites.size==0){
        Column (Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "YOU HAVE NOT ADDED ANY FAVOURITES!", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
    }
    else{
        Favscreen(results = Favourites, onNavigatetoDetail = onNavigatetoDetail)

        }
}

@Composable
fun Favscreen(results:List<favourites>, onNavigatetoDetail:(Int)->Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {
        items(results){
            FavItem(cat = it,onNavigatetoDetail)
        }
    }
}

@Composable
fun FavItem(cat: favourites, onNavigatetoDetail: (Int) -> Unit) {

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
            androidx.compose.material3.Text(
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
                   Favourites.remove(favourites(cat.id,cat.title,cat.image))
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .size(40.dp)
                .background(Color.White, shape = CircleShape)
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete"
            )
        }
    }
}