package com.example.recipeapp.ui.components

import com.example.recipeapp.data.remote.dto.recipes.RecipeDtoItem

data class ComponentTopRecipesState(
    val recipes: List<RecipeDtoItem> = emptyList(),
    val error: String = "",
    val loading: Boolean = true
)