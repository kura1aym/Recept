package com.example.recipeapp.ui.recipe_list_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.example.recipeapp.core.Screen
import com.example.recipeapp.core.lemonMilkFonts
import com.example.recipeapp.ui.custom_view.CustomShape

@Composable
fun RecipeListScreen(
    viewModel: RecipeListViewModel = hiltViewModel(),

){
    val isEditModeOn = viewModel.isEditModeOn.value
    val scaffoldState = rememberScaffoldState()
    RecipeListUi(
        scaffoldState = scaffoldState,
        viewModel = viewModel,
        isEditModeOn = isEditModeOn,
    )
}



@Composable
fun RecipeListUi(
    isEditModeOn: Boolean,
    scaffoldState: ScaffoldState,
    viewModel:RecipeListViewModel
){
    Scaffold(scaffoldState = scaffoldState) {padding ->
        LazyColumn(modifier = Modifier.fillMaxSize()){
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(LocalConfiguration.current.screenHeightDp.dp / 2)
                        .graphicsLayer {
                            shadowElevation = 8.dp.toPx()
                            shape = CustomShape()
                            clip = true
                        }
                        .drawBehind {
                            drawRect(color = Color(0xFF000000))
                        },
                ) {
                    SubcomposeAsyncImage(model = "",
                        contentDescription = null )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .drawBehind {
                                drawRect(Color.Transparent)
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        AnimatedVisibility(visible = isEditModeOn) {


                            }

                        }

                    }
                Text(
                    text = "Search across all recipes!" ,
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraLight,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onSurface,
                    fontFamily = lemonMilkFonts,

                )
                }
            }
        }

    }
