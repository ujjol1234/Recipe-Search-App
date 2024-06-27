package com.example.recipesearchapp.ViewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesearchapp.Model.SimillarRecipe
import com.example.recipesearchapp.Model.allrecipeservice
import kotlinx.coroutines.launch

class SimillarRecipeViewModel:ViewModel() {
    fun fetchcategories(id:String){
        viewModelScope.launch {   //Coroutine Scope is needed to call a suspend function
            try {
                val recipelist= allrecipeservice.getSimillarRecipe(id)
                Log.d("Uj","$recipelist")
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
                           val recipedetail:List<SimillarRecipe> = emptyList()

    )
    private val _categoryState= mutableStateOf(RecipeState())
    val categoryState: State<RecipeState> =_categoryState
}