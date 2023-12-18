package com.example.recipeapp.ui.recipe_list_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeListViewModel @Inject constructor(

) : ViewModel(){
    val isEditModeOn = mutableStateOf<Boolean>(false)
    private val _searchBoxState = mutableStateOf("")
    val searchBoxState: State<String> = _searchBoxState
    var searchJob: Job? = null

    fun onSearchBoxValueChanged(newValue: String) {

    }
    private fun searchRecipe() {

    }
    fun onClearSearchBoxButtonClicked() {}

    fun onSelectRadioButtonClicked(){}
    fun receiveFromRecipeListScreenEvents(){}
}

sealed interface ToRecipeListScreenEvents{
    object NavigateUp: ToRecipeListScreenEvents
    class ShowSnackbar(val message: String): ToRecipeListScreenEvents
}

sealed interface FromRecipeListScreenEvents{
    object DisableEditMode: FromRecipeListScreenEvents
    object DeleteButtonClicked: FromRecipeListScreenEvents
    object ChangeRefreshState : FromRecipeListScreenEvents
}