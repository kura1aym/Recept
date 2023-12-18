package com.example.recipeapp.data.remote.repository

import android.util.Log
import com.example.recipeapp.core.Resource
import com.example.recipeapp.data.local.RecipeDatabase
import com.example.recipeapp.data.local.RecipeEntity
import com.example.recipeapp.data.mapper.toCategoryDtoItem
import com.example.recipeapp.data.mapper.toLocalRecipeCategoryEntity
import com.example.recipeapp.data.mapper.toLocalRecipeEntity
import com.example.recipeapp.data.mapper.toModelLocalRecipe
import com.example.recipeapp.data.mapper.toRecipeDtoItem
import com.example.recipeapp.data.mapper.toRecipeEntity
import com.example.recipeapp.data.remote.RecipeApi
import com.example.recipeapp.data.remote.dto.categories.CategoryDtoItem
import com.example.recipeapp.data.remote.dto.recipes.RecipeDtoItem
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class RecipeRepositoryImpl @Inject constructor(
    private val recipeApi: RecipeApi,
    private val recipeDatabase: RecipeDatabase
) :
    RecipeRepository {
    private val recipeDao = recipeDatabase.dao
    override suspend fun getFirstFourRecipes(fetchFromRemote: Boolean): Resource<List<RecipeDtoItem>> {
        try {
            val shouldJustLoadFromCache = !fetchFromRemote && recipeDao.searchRecipe("").isNotEmpty()
            Log.d("reciperepository", "should just load from cache is, $shouldJustLoadFromCache")
            val myRecipes: List<RecipeEntity> = if (shouldJustLoadFromCache) {
                recipeDao.getFirstFourRecipes()
            } else {
                val remoteEntities = recipeApi.getRecipeList("snacks")
                recipeDao.clearRecipes()
                recipeDao.insertRecipes(remoteEntities.map {
                    it.toRecipeEntity()
                })
                recipeDao.getFirstFourRecipes()
            }
            val recipes = myRecipes.map {
                it.toRecipeDtoItem()
            }
            return Resource.Success<List<RecipeDtoItem>>(data = recipes)
        } catch (e: Exception) {
        Log.d("reciperepository", "unable to find first four recipes", e)
        return Resource.Error<List<RecipeDtoItem>>(error = "Unable to find top recipes: ${e.message}")
    }


}

    override suspend fun getRecipeByTitle(
        title: String,
        category: String
    ): Flow<Resource<RecipeDtoItem>> = flow {
        try {
            var recipeFromDb = recipeDao.getRecipeByTitle(recipeTitle = title.toString())
            Log.d("reciperepository", "recipeFromDb in get recipe by category is, $category")
            if (recipeFromDb == null) {
                recipeDao.clearRecipes()
                val remoteEntities = recipeApi.getRecipeList(category)
                recipeDao.insertRecipes(remoteEntities.map {
                    it.toRecipeEntity()
                })
                recipeFromDb = recipeDao.getRecipeByTitle(recipeTitle = title)
            }
            val recipeEntity = recipeFromDb?.toRecipeDtoItem() ?: RecipeDtoItem()
            emit(Resource.Success<RecipeDtoItem>(recipeEntity))
        } catch (e: Exception) {
            Log.d("reciperepository", "unable to get recipe by title, \n $e")
            emit(Resource.Error<RecipeDtoItem>(error = "unable to find recipe by title $title"))
        }
    }

    override suspend fun getCategories(): Flow<Resource<List<CategoryDtoItem>>> = flow {
        try {
            emit(Resource.Loading<List<CategoryDtoItem>>())
            val categoriesBeforeApiCall = recipeDao.getAllCategories()
            emit(Resource.Success<List<CategoryDtoItem>>(data = categoriesBeforeApiCall.map { it.toCategoryDtoItem() }))
            val recipes = recipeApi.getCategory()

            if (recipes.isNotEmpty()) {
                recipeDao.deleteAllCategories()
                recipeDao.insertLocalCategories(recipes.map { it.toLocalRecipeCategoryEntity() })
            }
            val localCategories = recipeDao.getAllCategories()
            emit(Resource.Success<List<CategoryDtoItem>>(data = localCategories.map { it.toCategoryDtoItem() }))
        } catch (e: Exception) {

            try {
                val recipes = recipeDao.getAllCategories()
                emit(Resource.Success<List<CategoryDtoItem>>(recipes.map { it.toCategoryDtoItem() }))
            } catch (e: Exception) {
                // something really wrong has occurred, giving error message
                Log.d("reciperepository", "unable to get categories, $e")
                emit(Resource.Error<List<CategoryDtoItem>>("unable to load categories, please try again later"))
            }
        }
    }

    override suspend fun getRecipesByCategory(
        recipe: String,
        category: String,
        page: Int,
        pageSize: Int,
        fetchFromRemote: Boolean,
        getSavedRecipes: Boolean
    ): Resource<List<RecipeDtoItem>> {
        if (getSavedRecipes) {
            val myRecipes = recipeDao.searchSavedRecipe(recipe = recipe)
            val recipes = myRecipes.map {
                it.toModelLocalRecipe().toRecipeDtoItem()
            }
            try {

                val startingIndex = page * pageSize
                val endingIndex = startingIndex + pageSize

                if (startingIndex < recipes.size) {
                    return if (endingIndex < recipes.size) {
                        Resource.Success<List<RecipeDtoItem>>(data = recipes.slice(startingIndex until startingIndex + pageSize))
                    } else {
                        Resource.Success<List<RecipeDtoItem>>(data = recipes.slice(startingIndex until recipes.size))
                    }
                } else {
                    return Resource.Success<List<RecipeDtoItem>>(data = emptyList())
                }

            } catch (e: Exception) {
                return Resource.Error<List<RecipeDtoItem>>("unable to load data, please try again later")
            }
        } else {
            try {
                val shouldJustLoadFromCache =
                    !fetchFromRemote && recipeDao.getRecipeByTag(
                        category = category,
                        recipe = recipe
                    )
                        .isNotEmpty()
                Log.d(
                    "reciperepository",
                    "should just load from cache is $shouldJustLoadFromCache, fetch from remote is $fetchFromRemote, db result is ${
                        recipeDao.getRecipeByTag(
                            category = category,
                            recipe = recipe
                        ).size
                    }"
                )

                val myRecipes: List<RecipeEntity> = if (!shouldJustLoadFromCache) {
                    if(recipeDao.searchRecipe("").isEmpty()){
                        recipeDao.clearRecipes()
                    }
                    val remoteEntities = recipeApi.getRecipeList(category)
                    recipeDao.insertRecipes(remoteEntities.map {
                        it.toRecipeEntity()
                    })
                    recipeDao.getRecipeByTag(category = category, recipe = recipe)
                } else {
                    Log.d("reciperepository", "inside should just load from cache")
                    recipeDao.getRecipeByTag(category = category, recipe = recipe)
                }
                Log.d("reciperepository", "recipes size are ${myRecipes.size}")
                val recipes = myRecipes.map {
                    it.toRecipeDtoItem()
                }
                val startingIndex = page * pageSize
                Log.d(
                    "reciperepository",
                    "starting index is $startingIndex, page is $page, pageSize is $pageSize, recipes size is ${recipes.size}"
                )
                val endingIndex = startingIndex + pageSize

                if (startingIndex < recipes.size) {
                    return if (endingIndex < recipes.size) {
                        Log.d(
                            "reciperepository",
                            "starting index is $startingIndex, ending index is ${startingIndex + pageSize}, recipe is ${
                                recipes.slice(
                                    startingIndex until startingIndex + pageSize
                                )
                            }"
                        )
                        Resource.Success<List<RecipeDtoItem>>(data = recipes.slice(startingIndex until startingIndex + pageSize))
                    } else {
                        Resource.Success<List<RecipeDtoItem>>(data = recipes.slice(startingIndex until recipes.size))
                    }
                } else {
                    return Resource.Success<List<RecipeDtoItem>>(data = emptyList())
                }
            } catch (exception: Exception) {
                Log.d("reciperepository", "unable to load recipes by category\n $exception")
                return Resource.Error<List<RecipeDtoItem>>("unable to load data, please try again later")
            }
        }
    }

    override suspend fun getSavedRecipes(): Resource<List<Recipe>> {
        return try {
            val savedRecipes = recipeDao.getSavedRecipes()
            val modelLocalRecipe = savedRecipes.map { it.toModelLocalRecipe() }
            Resource.Success<List<Recipe>>(data = modelLocalRecipe)
        } catch (e: Exception) {
            Log.d("reciperepository", "unable to get saved recipes\n $e")
            Resource.Error<List<Recipe>>(error = "unable to load saved recipes, please try again later")
        }
    }

    override suspend fun saveRecipe(recipeDtoItem: RecipeDtoItem): Resource<String> {
        return try {
            val localRecipeEntity = recipeDtoItem.toLocalRecipeEntity()
            recipeDao.saveRecipe(localRecipeEntity = localRecipeEntity)
            Resource.Success<String>("Recipe saved successfully")
        } catch (e: Exception) {
            Log.d("reciperepository", "unable to save recipe\n $e")
            Resource.Error<String>("unable to save recipe, please try again")
        }
    }

    override suspend fun getLocalRecipeByTitle(title: String): Resource<Recipe?> {
        try {
            val localRecipeEntity = recipeDao.getSavedRecipeByTitle(title = title)
                ?: return Resource.Success<Recipe?>(data = null)
            val modelRecipeEntity = localRecipeEntity.toModelLocalRecipe()
            return Resource.Success<Recipe?>(data = modelRecipeEntity)
        } catch (e: Exception) {
            Log.d("reciperepository", "unable to get saved recipe by title\n $e")
            return Resource.Error<Recipe?>(error = "Unable to load recipes")
        }
    }

    override suspend fun deleteSelectedSavedRecipes(recipeTitles: List<String>): String {
        return try {
            recipeDao.deleteLocalRecipes(titles = recipeTitles)
            "Recipes DELETED successfully"
        } catch (e: Exception) {
            Log.d("reciperepository", "unable to delete recicpes\n $e")
            "Recipes NOT deleted successfully, please try again"
        }
    }

    override suspend fun getRecipes(
        recipe: String,
        page: Int,
        pageSize: Int,
        fetchFromRemote: Boolean,
    ): Resource<List<RecipeDtoItem>> {

        try {
            val shouldJustLoadFromCache =
                !fetchFromRemote && recipeDao.searchRecipe("").isNotEmpty()
            val myRecipes: List<RecipeEntity> = if (!shouldJustLoadFromCache) {

                recipeDao.clearRecipes()
                val remoteEntities = recipeApi.getRecipeList(recipe)
                recipeDao.insertRecipes(remoteEntities.map {
                    it.toRecipeEntity()
                })
                recipeDao.searchRecipe("")
            } else {
                Log.d("reciperepository", "inside should just load from cache")
                recipeDao.searchRecipe("")
            }
            val recipes = myRecipes.map {
                it.toRecipeDtoItem()
            }
            val startingIndex = page * pageSize
            Log.d(
                "reciperepository",
                "starting index is $startingIndex, page is $page, pageSize is $pageSize, recipes size is ${recipes.size}"
            )
            val endingIndex = startingIndex + pageSize

            if (startingIndex < recipes.size) {
                return if (endingIndex < recipes.size) {
                    Log.d(
                        "reciperepository",
                        "starting index is $startingIndex, ending index is ${startingIndex + pageSize}, recipe is ${
                            recipes.slice(
                                startingIndex until startingIndex + pageSize
                            )
                        }"
                    )
                    Resource.Success<List<RecipeDtoItem>>(data = recipes.slice(startingIndex until startingIndex + pageSize))
                } else {
                    Resource.Success<List<RecipeDtoItem>>(data = recipes.slice(startingIndex until recipes.size))
                }
            } else {
                return Resource.Success<List<RecipeDtoItem>>(data = emptyList())
            }
        } catch (exception: Exception) {
            return Resource.Error<List<RecipeDtoItem>>("unable to load data, please try again later")
        }
    }
}