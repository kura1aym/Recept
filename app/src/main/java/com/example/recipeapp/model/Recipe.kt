package com.example.recipeapp.model

data class RecipeModel(
    val id: Long,
    val authorId: Long,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val steps: List<String>,
    val imageUrl: List<String?>,
    val likes: Long,
    val comments: List<String>,
    val isFavorite: Boolean
)

data class UserModel(
    val userId: String,
    val username: String,
    val email: String,
    val password: String,
    val recipesId: List<Long>,
    val favoriteRecipesId: List<Long>
)