package com.example.recipeapp.ui

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipeapp.R
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
            startDestination = Screen.OnBoardingScreen.route
        ) {
            composable(route = Screen.OnBoardingScreen.route) {
                OnboardingScreen(navController = navController)
            }
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
                RecipeScreen(navController = navController,
                    windowSize = windowSize)
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
                RecipeListScreen(navController = navController,
                    windowSize = windowSize
                )
            }
        }
    }
}

@Composable
fun OnboardingScreen(navController: NavController) {
    val totalPages = 3
    var currentPage by remember { mutableStateOf(0) }

    Column(modifier = Modifier.background(MaterialTheme.colorScheme.background) ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
        Image(painter = painterResource(id = R.drawable.logoo), modifier = Modifier
            .width(150.dp)
            .height(100.dp), contentDescription = "App Logo")
        }
        OnboardingPage(currentPage)

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
           OutlinedButton(modifier = Modifier,
                    shape = RoundedCornerShape(50),onClick = {
                navController.navigate(Screen.HomeScreen.route) {
                    Screen.OnBoardingScreen.route
                }
            }) {
                Text("Skip" ,color = Color.Black)
            }
            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(modifier = Modifier, shape = RoundedCornerShape(50), onClick = {
                if (currentPage < totalPages - 1) {
                    currentPage++
                } else {
                    navController.navigate(Screen.HomeScreen.route) {
                        Screen.OnBoardingScreen.route
                    }
                }
            }) {
                Text("Next", color = Color.Black)
            }
        }
    }
}

@Composable
fun OnboardingPage(page: Int) {
    when (page) {
        0 -> OnboardingScreen1()
        1 -> OnboardingScreen2()
        2 -> OnboardingScreen3()
    }
}


    @Composable
    fun OnboardingScreen1() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background) // Use the correct color for your background
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()

            ) {
                Text(
                    text = "Welcome",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 32.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                Image(
                    painter = painterResource(id = R.drawable.backimage), modifier = Modifier.padding(bottom = 10.dp), // replace with your drawable resource
                    contentDescription = "Phone with Recipes",
                )}
                Text(
                    text = "Search for recipes",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                            .padding(bottom = 10.dp)
                )
                Text(
                    text = "Browse and find any recipe you are looking for.\nAlso you can search by the ingredient you like.",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 100.dp)
                )



            }
        }
    }



@Composable
fun OnboardingScreen2() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background) // Use the correct color for your background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()

        ) {
            Text(
                text = "Recipe Finder App",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 32.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.backimage), modifier = Modifier.padding(bottom = 10.dp), // replace with your drawable resource
                    contentDescription = "Phone with Recipes",
                )}
            Text(
                text = "Save your liked recipes",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
                    .padding(bottom = 10.dp)
            )
            Text(
                text = "Save all your favourite recipes and get it later locally.",color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 100.dp)
            )



        }
    }
}

@Composable
fun OnboardingScreen3() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background) // Use the correct color for your background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()

        ) {
            Text(
                text = "Let's cook",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 32.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bg_3), modifier = Modifier.padding(bottom = 10.dp), // replace with your drawable resource
                    contentDescription = "Phone with Recipes",
                )}
            Text(
                text = "Find your recipe and cook it",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
                    .padding(bottom = 10.dp)
            )
            Text(
                text = "Pla for the whole the week, and add ingredients, browse recipes and add it easily and fast",color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 100.dp)
            )



        }
    }
}








