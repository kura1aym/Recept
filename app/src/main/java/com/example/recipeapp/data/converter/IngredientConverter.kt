package com.example.recipeapp.data.converter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.example.recipeapp.data.remote.dto.recipes.Ingredient

class IngredientConverter{
    @TypeConverter
    fun fromIngredientToStringToJson(ingredients: List<Ingredient>): String? = Gson().toJson(ingredients)

    @TypeConverter
    fun fromStringToIngredients(json: String): List<Ingredient> = Gson().fromJson(json, Array<Ingredient>::class.java).toList()
}