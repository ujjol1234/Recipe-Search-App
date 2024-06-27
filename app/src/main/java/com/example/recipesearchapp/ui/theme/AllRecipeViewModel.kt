package com.example.recipesearchapp.ui.theme

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesearchapp.Model.allrecipeservice
import com.example.recipesearchapp.Model.result
import kotlinx.coroutines.launch

class AllRecipeViewModel:ViewModel() {
    init {
        fetchcategories()
    }

    private fun fetchcategories(){
        viewModelScope.launch {   //Coroutine Scope is needed to call a suspend function
            try {
                val recipelist= allrecipeservice.getcategories()
                _categoryState.value=_categoryState.value.copy(loading=false,
                    Error=null,
                    list=recipelist.results)
            }
            catch (e:Exception){
                _categoryState.value=_categoryState.value.copy(loading=false,
                    Error="${e.message}")
            }
        }
    }
    data class RecipeState(val loading:Boolean=true,
                           val Error:String?=null,
                           val list:List<result> = emptyList()

    )

    private val _categoryState= mutableStateOf(RecipeState())
    val categoryState: State<RecipeState> =_categoryState
}
