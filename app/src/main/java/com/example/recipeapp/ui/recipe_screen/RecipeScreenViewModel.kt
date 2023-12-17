package com.example.recipeapp.ui.recipe_screen


import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.core.Constants
import com.example.recipeapp.core.Resource
import com.example.recipeapp.data.mapper.toRecipeDtoItem
import com.example.recipeapp.data.remote.dto.recipes.RecipeDtoItem
import com.example.recipeapp.domain.repository.RecipeRepository


import com.example.recipeapp.ui.recipe_screen.components.RecipeScreenState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import kotlin.math.ceil

@HiltViewModel
class RecipeScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository,
   ) : ViewModel() {
    private val _recipeState = mutableStateOf<RecipeScreenState>(RecipeScreenState())
    val recipeState: State<RecipeScreenState> = _recipeState

    private val recipeTitle = mutableStateOf("")
    private val recipeCategory = mutableStateOf("")

    private val _uiRecipeScreenEvents: Channel<RecipeScreenEvents> = Channel()
    val uiRecipeScreenEvents = _uiRecipeScreenEvents.receiveAsFlow()

    private val numberOfPersonsState = mutableStateOf<Int>(1)
    val numberOfPersons = numberOfPersonsState as State<Int>

    private val _favouriteState = mutableStateOf(RecipeSaveState.UNABLE_TO_SAVE)
    val favouriteState: State<RecipeSaveState> = _favouriteState

    init {
        val recipe = savedStateHandle.get<String>(Constants.RECIPE_SCREEN_RECIPE_TITLE_KEY)
        val decodedTitle = URLDecoder.decode(recipe, StandardCharsets.UTF_8.toString())
        val category = savedStateHandle.get<String>(Constants.RECIPE_SCREEN_RECIPE_CATEGORY_KEY)
        val decodedCategory = URLDecoder.decode(category, StandardCharsets.UTF_8.toString())
        viewModelScope.launch {
            recipeTitle.value = decodedTitle ?: ""

            recipeCategory.value = decodedCategory ?: ""
            Log.d("recipescreenviewmodel", "recipe is $recipe")

            val shouldLoadFromSavedRecipes = savedStateHandle.get<Boolean>(Constants.RECIPE_SCREEN_SHOULD_LOAD_FROM_SAVED_RECIPES) ?: true
            Log.d("recipescreenviewmodel", "should load from saved recipes is $shouldLoadFromSavedRecipes")

        }
    }


    fun sendRecipeScreenUiEvent(uiEvents: RecipeScreenEvents) {
        viewModelScope.launch {
            when (uiEvents) {
                is RecipeScreenEvents.ShowSnackbar -> _uiRecipeScreenEvents.send(
                    RecipeScreenEvents.ShowSnackbar(
                        message = uiEvents.message
                    )
                )
            }
        }
    }



    fun onSliderValueChanged(newValue: Float){
        Log.d("recipescreenviewmodel", "new value of slider is $newValue value of ceil is ${ceil(newValue*10)}")
        numberOfPersonsState.value = if(newValue <= 0.1f) 1 else (newValue*10).toInt()
    }

}

sealed interface RecipeScreenEvents {
    class ShowSnackbar(val message: String) : RecipeScreenEvents
}