package com.example.recipeapp.data.mapper

import com.example.recipeapp.data.local.LocalRecipeCategoryEntity
import com.example.recipeapp.data.local.LocalRecipeEntity
import com.example.recipeapp.data.local.RecipeEntity
import com.example.recipeapp.data.remote.dto.categories.CategoryDtoItem
import com.example.recipeapp.data.remote.dto.recipes.RecipeDtoItem
import com.example.recipeapp.domain.model.Recipe



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

fun RecipeDtoItem.toLocalRecipeEntity(): LocalRecipeEntity = LocalRecipeEntity(
    imageUrl = imageUrl,
    ingredient = ingredient,
    method = method,
    tag = tag,
    title = title
)

fun RecipeEntity.toLocalRecipeEntity(): LocalRecipeEntity = LocalRecipeEntity(
    imageUrl = imageUrl,
    ingredient = ingredient,
    method = method,
    tag = tag,
    title = title
)

fun LocalRecipeEntity.toModelLocalRecipe(): Recipe = Recipe(
    imageUrl = imageUrl,
    ingredient = ingredient,
    method = method,
    tag = tag,
    title = title
)

fun Recipe.toRecipeDtoItem(): RecipeDtoItem = RecipeDtoItem(
    imageUrl = imageUrl,
    ingredient = ingredient,
    method = method,
    tag = tag,
    title = title
)

fun CategoryDtoItem.toLocalRecipeCategoryEntity(): LocalRecipeCategoryEntity = LocalRecipeCategoryEntity(
    category = category,
    imageUrl = imageUrl
)

fun LocalRecipeCategoryEntity.toCategoryDtoItem(): CategoryDtoItem = CategoryDtoItem(
    category = category,
    imageUrl = imageUrl
)