package com.example.recipeapp.domain.model

import com.example.recipeapp.data.remote.dto.recipes.Ingredient

data class Recipe(
    val imageUrl: String = "",
    val ingredient: List<Ingredient> = listOf(),
    val method: List<String> = listOf(),
    val tag: String = "",
    val title: String = ""
)

