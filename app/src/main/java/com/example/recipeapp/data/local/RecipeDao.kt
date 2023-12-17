package com.example.recipeapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface RecipeDao{
    @Insert(onConflict = REPLACE)
    suspend fun insertRecipes(recipeEntities: List<RecipeEntity>)

    @Query("DELETE FROM recipeentity")
    suspend fun clearRecipes()

    @Query("""
        SELECT *
        FROM recipeentity
        WHERE LOWER(title) LIKE '%' || LOWER(:query) || '%'
    """)
    suspend fun searchRecipe(query: String): List<RecipeEntity>

    @Query("SELECT * FROM recipeentity WHERE title LIKE '%pizza%' OR title LIKE '%burger%' LIMIT 10")
    suspend fun getFirstFourRecipes(): List<RecipeEntity>

    @Query("SELECT * FROM recipeentity WHERE title = :recipeTitle")
    suspend fun getRecipeByTitle(recipeTitle: String): RecipeEntity?

    @Query("SELECT * FROM recipeentity WHERE tag = :category AND LOWER(title) LIKE '%' || LOWER(:recipe) || '%' ")
    suspend fun getRecipeByTag(category: String, recipe: String): List<RecipeEntity>
}