package com.example.recipesearchapp.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesearchapp.Model.RecipeDetailResponse
import com.example.recipesearchapp.Model.allrecipeservice
import com.example.recipesearchapp.Model.nutrition
import kotlinx.coroutines.launch

class RecipeDetailViewModel:ViewModel() {
    fun fetchcategories(id:String){
        viewModelScope.launch {   //Coroutine Scope is needed to call a suspend function
            try {
                val recipelist= allrecipeservice.getRecipeInfo(id = id)
                _categoryState.value=_categoryState.value.copy(loading=false,
                    Error=null,
                   recipedetail =recipelist)
            }
            catch (e:Exception){
                _categoryState.value=_categoryState.value.copy(loading=false,
                    Error="${e.message}")
            }
        }
    }
    data class RecipeState(val loading:Boolean=true,
                           val Error:String?=null,
                           val recipedetail: RecipeDetailResponse = RecipeDetailResponse(emptyList(),
                               nutrition(emptyList()),
                               ""
                           )

    )

    private val _categoryState= mutableStateOf(RecipeState())
    val categoryState: State<RecipeState> =_categoryState

}