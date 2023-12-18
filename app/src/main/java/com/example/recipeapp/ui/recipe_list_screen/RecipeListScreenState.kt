package com.example.recipeapp.ui.recipe_list_screen

import com.example.recipeapp.data.remote.dto.recipes.RecipeDtoItem

data class RecipeListScreenState(
    val isLoading: Boolean = false,
    val items: List<RecipeDtoItem> = emptyList(),
    val error: String = "",
    val endReached: Boolean = false,
    val page: Int = 0
)