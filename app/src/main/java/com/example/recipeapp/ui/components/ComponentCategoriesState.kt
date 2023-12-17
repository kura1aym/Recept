package com.example.recipeapp.ui.components

import com.example.recipeapp.data.remote.dto.categories.CategoryDtoItem

data class ComponentCategoriesState(
    val isLoading: Boolean = true,
    val categories: List<CategoryDtoItem> = emptyList(),
    val error: String = "",
)