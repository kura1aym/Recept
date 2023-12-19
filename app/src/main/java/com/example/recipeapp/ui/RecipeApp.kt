package com.example.recipeapp.ui

import android.Manifest
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipeapp.core.Constants
import com.example.recipeapp.core.Screen
import com.example.recipeapp.ui.screens.categories_screen.CategoriesScreen
import com.example.recipeapp.ui.screens.home_screen.HomeScreen
import com.example.recipeapp.ui.screens.recipe_list_screen.RecipeListScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.example.recipeapp.ui.screens.recipe_screen.RecipeScreen


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RecipeApp(
    windowSize: WindowWidthSizeClass
) {
    val permissionState =
        rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )
    val allPermissionsGranted by remember { mutableStateOf(permissionState.allPermissionsGranted) }
    val navController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.HomeScreen.route
        ) {
            composable(route = Screen.HomeScreen.route) {
                HomeScreen(
                    navController = navController,
                    windowSize = windowSize
                ) {
                }
            }
            composable(route = Screen.RecipeScreen.route + "/{${Constants.RECIPE_SCREEN_RECIPE_TITLE_KEY}}/{${Constants.RECIPE_SCREEN_RECIPE_CATEGORY_KEY}}/{${Constants.RECIPE_SCREEN_SHOULD_LOAD_FROM_SAVED_RECIPES}}",
                arguments = listOf(
                    navArgument(name = Constants.RECIPE_SCREEN_RECIPE_TITLE_KEY) {
                        type = NavType.StringType
                    },
                    navArgument(name = Constants.RECIPE_SCREEN_RECIPE_CATEGORY_KEY) {
                        type = NavType.StringType
                    },
                    navArgument(name = Constants.RECIPE_SCREEN_SHOULD_LOAD_FROM_SAVED_RECIPES)
                    {
                        type = NavType.BoolType
                    },
                )) {
                RecipeScreen(navController = navController)
            }

            composable(route = Screen.CategoriesScreen.route){
                CategoriesScreen(
                   navController = navController
                )
            }
            composable(
                route = Screen.RecipeListScreen.route + "/{${Constants.RECIPE_LIST_SCREEN_RECIPE_CATEGORY_KEY}}/{${Constants.RECIPE_LIST_SCREEN_RECIPE_IMAGE_URL_KEY}}/{${Constants.RECIPE_SCREEN_SHOULD_LOAD_FROM_SAVED_RECIPES}}",
                arguments = listOf(
                    navArgument(name = Constants.RECIPE_LIST_SCREEN_RECIPE_CATEGORY_KEY) {
                        type = NavType.StringType
                    },
                    navArgument(name = Constants.RECIPE_LIST_SCREEN_RECIPE_IMAGE_URL_KEY)
                    {
                        type = NavType.StringType
                    },
                    navArgument(name = Constants.RECIPE_SCREEN_SHOULD_LOAD_FROM_SAVED_RECIPES)
                    {
                        type = NavType.BoolType
                    },
                )
            ) {
                RecipeListScreen(navController = navController)
            }
        }
    }
}



