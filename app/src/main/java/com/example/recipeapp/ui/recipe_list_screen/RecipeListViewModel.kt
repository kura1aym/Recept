package com.example.recipeapp.ui.recipe_list_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class RecipeListViewModel @Inject constructor(

) : ViewModel(){
    val isEditModeOn = mutableStateOf<Boolean>(false)
}