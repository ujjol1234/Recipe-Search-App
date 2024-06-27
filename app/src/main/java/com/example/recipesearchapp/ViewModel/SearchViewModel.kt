package com.example.recipesearchapp.ViewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesearchapp.Model.SearchResponse
import com.example.recipesearchapp.Model.allrecipeservice
import kotlinx.coroutines.launch

class SearchViewModel:ViewModel() {
    fun fetchcategories(query:String,cuisine:String,excludeIngredients:String,minProtein:String,maxCarbs:String){
        Log.d("UJ","$query $cuisine $excludeIngredients  $minProtein $maxCarbs")
        viewModelScope.launch {   //Coroutine Scope is needed to call a suspend function
            try {
                val recipelist= allrecipeservice.getSearchResults(query = query,cuisine=cuisine,
                    excludeIngredients=excludeIngredients,minProtein=minProtein,maxCarbs=maxCarbs)
                _categoryState.value=_categoryState.value.copy(loading=false,
                    Error=null,
                    searchresults =recipelist)
            }
            catch (e:Exception){
                _categoryState.value=_categoryState.value.copy(loading=false,
                    Error="${e.message}")
            }
        }
    }
    data class RecipeState(val loading:Boolean=true,
                           val Error:String?=null,
                           val searchresults: SearchResponse = SearchResponse(emptyList())

    )
    private val _categoryState= mutableStateOf(RecipeState())
    val categoryState: State<RecipeState> =_categoryState
}