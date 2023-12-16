package com.example.recipeapp.model

data class User(
    val userId: String,
    val username: String,
    val email: String,
    val password: String,
    val recipesId: List<Long>,
    val favoriteRecipesId: List<Long>
)