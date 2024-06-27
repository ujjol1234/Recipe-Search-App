package com.example.recipesearchapp.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesearchapp.Model.allrecipeservice
import com.example.recipesearchapp.Model.recipes
import kotlinx.coroutines.launch

class PopularRecipeViewModel:ViewModel() {
    init {
        fetchcategories()
    }
    private fun fetchcategories(){
        viewModelScope.launch {   //Coroutine Scope is needed to call a suspend function
            try {
                val recipelist= allrecipeservice.getpopularrecipe()
                _categoryState.value=_categoryState.value.copy(loading=false,
                    Error=null,
                    list=recipelist.recipes)
            }
            catch (e:Exception){
                _categoryState.value=_categoryState.value.copy(loading=false,
                    Error="${e.message}")
            }
        }
    }
    data class RecipeState(val loading:Boolean=true,
                           val Error:String?=null,
                           val list:List<recipes> = emptyList()

    )

    private val _categoryState= mutableStateOf(RecipeState())
    val categoryState: State<RecipeState> =_categoryState
}