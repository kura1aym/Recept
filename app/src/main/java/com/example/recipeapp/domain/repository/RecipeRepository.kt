package com.example.recipeapp.domain.repository

import com.example.recipeapp.core.Resource
import com.example.recipeapp.data.remote.dto.categories.CategoryDtoItem
import com.example.recipeapp.data.remote.dto.recipes.RecipeDtoItem
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getRecipes(
        recipe: String,
        page: Int,
        pageSize: Int,
        fetchFromRemote: Boolean
    ): Resource<List<RecipeDtoItem>>

    suspend fun getFirstFourRecipes(fetchFromRemote: Boolean): Resource<List<RecipeDtoItem>>

    suspend fun getRecipeByTitle(title: String, category: String): Flow<Resource<RecipeDtoItem>>

    suspend fun getCategories(): Flow<Resource<List<CategoryDtoItem>>>

    suspend fun getRecipesByCategory(
        recipe: String,
        category: String,
        page: Int,
        pageSize: Int,
        fetchFromRemote: Boolean,
    ): Resource<List<RecipeDtoItem>>
}