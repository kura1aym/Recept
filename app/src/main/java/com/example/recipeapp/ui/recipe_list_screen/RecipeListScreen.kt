package com.example.recipeapp.ui.recipe_list_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import coil.compose.SubcomposeAsyncImage
import com.example.recipeapp.core.Screen


@Composable
fun RecipeListScreen(){
    val scaffoldState = rememberScaffoldState()
    RecipeListUi(
        scaffoldState = scaffoldState
    )
}



@Composable
fun RecipeListUi(
    scaffoldState: ScaffoldState,
){
    Scaffold(scaffoldState = scaffoldState) {padding ->
        LazyColumn(modifier = Modifier.fillMaxSize()){
            item {
                Box(){
                    SubcomposeAsyncImage(model = "",
                        contentDescription = null )
                    Row(){

                    }
                    Text(
                        text = ""
                    )
                }
            }
        }

    }
}