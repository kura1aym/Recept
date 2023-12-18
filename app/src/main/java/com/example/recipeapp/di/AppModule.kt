package com.example.recipeapp.di

import android.app.Application
import androidx.room.Room
import com.example.recipeapp.core.Constants
import com.example.recipeapp.data.local.RecipeDatabase
import com.example.recipeapp.data.remote.RecipeApi
import com.example.recipeapp.data.remote.repository.RecipeRepositoryImpl
import com.example.recipeapp.domain.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRecipeApi(): RecipeApi = Retrofit
        .Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RecipeApi::class.java)

    @Provides
    @Singleton
    fun providesRecipeDatabase(app: Application): RecipeDatabase = Room
        .databaseBuilder(app, RecipeDatabase::class.java, RecipeDatabase.DATABASE_NAME)
        .build()

    @Provides
    @Singleton
    fun providesRecipeRepository(recipeApi: RecipeApi, database: RecipeDatabase): RecipeRepository =
        RecipeRepositoryImpl(recipeApi = recipeApi, recipeDatabase = database)
}