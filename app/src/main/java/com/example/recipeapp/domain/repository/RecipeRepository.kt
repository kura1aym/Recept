package com.example.recipeapp.domain.repository

import com.example.recipeapp.core.Resource
import com.example.recipeapp.data.remote.dto.categories.CategoryDtoItem
import com.example.recipeapp.data.remote.dto.recipes.RecipeDtoItem
import com.example.recipeapp.model.ModelLocalRecipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getRecipes(
        recipe: String,
        page: Int,
        pageSize: Int,
        fetchFromRemote: Boolean
    ): Resource<List<RecipeDtoItem>>

    suspend fun getFirstFourRecipes(): Resource<List<RecipeDtoItem>>

    suspend fun getRecipeByTitle(title: String, category: String): Flow<Resource<RecipeDtoItem>>

    suspend fun getCategories(): Flow<Resource<List<CategoryDtoItem>>>

    suspend fun getRecipesByCategory(
        recipe: String,
        category: String,
        page: Int,
        pageSize: Int,
        fetchFromRemote: Boolean,
        getSavedRecipes: Boolean,
    ): Resource<List<RecipeDtoItem>>

    suspend fun getSavedRecipes(): Resource<List<ModelLocalRecipe>>

    suspend fun saveRecipe(recipeDtoItem: RecipeDtoItem): Resource<String>

    suspend fun getLocalRecipeByTitle(title: String): Resource<ModelLocalRecipe?>

    suspend fun deleteSelectedSavedRecipes(recipeTitles: List<String>): String
}
