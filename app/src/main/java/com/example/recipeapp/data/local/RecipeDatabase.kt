package com.example.recipeapp.data.local

import com.example.recipeapp.data.converter.IngredientConverter
import com.example.recipeapp.data.converter.MethodConverter
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
@Database(
    entities = [RecipeEntity::class],
    version = 1,
)
@TypeConverters(IngredientConverter::class, MethodConverter::class)
abstract class RecipeDatabase : RoomDatabase(){
    abstract val dao: RecipeDao
    companion object{
        const val  DATABASE_NAME = "recipedatabase"
    }
}