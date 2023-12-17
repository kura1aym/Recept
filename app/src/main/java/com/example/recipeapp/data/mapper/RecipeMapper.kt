package com.example.recipeapp.data.mapper

import com.example.recipeapp.data.local.RecipeEntity
import com.example.recipeapp.data.remote.dto.recipes.RecipeDtoItem


fun RecipeEntity.toRecipeDtoItem(): RecipeDtoItem = RecipeDtoItem(
    imageUrl = imageUrl,
    ingredient = ingredient,
    method = method,
    tag = tag,
    title = title,
)

fun RecipeDtoItem.toRecipeEntity(): RecipeEntity = RecipeEntity(
    imageUrl = imageUrl,
    ingredient = ingredient,
    method = method,
    tag = tag,
    title = title
)