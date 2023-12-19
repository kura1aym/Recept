package com.example.recipeapp.domain.usercases



import android.util.Log
import com.example.recipeapp.core.Resource
import com.example.recipeapp.domain.repository.RecipeRepository
import com.example.recipeapp.ui.screens.recipe_screen.RecipeSaveState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class UseCaseGetRecipeSavedStatus @Inject constructor(private val recipeRepository: RecipeRepository) {
    suspend operator fun invoke(title: String): Flow<RecipeSaveState> = flow {
        when(val recipe =  recipeRepository.getLocalRecipeByTitle(title = title)){
            is Resource.Error -> {
                emit(RecipeSaveState.NOT_SAVED)
            }
            is Resource.Loading -> {
                emit(RecipeSaveState.NOT_SAVED)
            }
            is Resource.Success -> {
                Log.d("ucgetrecipesavedstatus", "recipe is ${recipe.data} title is $title")
                if((recipe.data == null)){
                    emit(RecipeSaveState.NOT_SAVED)
                }
                else{
                    emit(RecipeSaveState.ALREADY_EXISTS)
                }
            }
        }
    }
}