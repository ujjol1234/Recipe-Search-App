package com.example.recipesearchapp.View

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.recipesearchapp.Navigation
import com.example.recipesearchapp.Screen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainView() {
    val scaffoldState = rememberScaffoldState()
    val controller: NavController = rememberNavController()
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val tabTitles = listOf("All Recipes", "Popular Recipes")
    val (selectedTabIndex, setSelectedTabIndex) = androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(0) }

    Scaffold (

        topBar = {
            Row (Modifier.fillMaxWidth()){
                TopAppBar(
                    title = {
                        Row (Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically){
                            Text(text = "Recipes")
                            IconButton(onClick = {
                                controller.navigate(Screen.SearchScreen.route)
                            }){
                                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")}
                        } },
                    elevation = 12.dp
                )
            }

        },
        scaffoldState = scaffoldState,
    ){
        Navigation(navController = controller,pd=it)
    }
}
