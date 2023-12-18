package com.example.recipeapp.ui.screens.categories_screen.components

import com.example.recipeapp.data.remote.dto.categories.CategoryDtoItem

data class CategoryListState(
    val isLoading: Boolean = false,
    val categories: List<CategoryDtoItem> = emptyList(),
    val error: String = "",
)
