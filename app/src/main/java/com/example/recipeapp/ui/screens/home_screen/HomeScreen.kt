package com.example.recipeapp.ui.screens.home_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuOpen
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.example.recipeapp.R
import com.example.recipeapp.core.Screen
import com.example.recipeapp.ui.RectangleMinusSemicircleShape
import com.example.recipeapp.ui.theme.MyPadding
import com.example.recipeapp.ui.theme.lemonMilkFonts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavHostController,
    onFinishCalled: () -> Unit,
) {
    val topRecipesState by viewModel.topRecipes
    val categoriesState by viewModel.categoriesState
    val scaffoldState = rememberScaffoldState()
    val url = URLEncoder.encode(
        "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=481&q=80",
        StandardCharsets.UTF_8.toString()
    )
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.uiEvents.collectLatest {
            when (it) {
                HomeScreenUiEvents.CloseNavDrawer -> {
                    scaffoldState.drawerState.close()
                }
                HomeScreenUiEvents.OpenNavDrawer -> {
                    scaffoldState.drawerState.open()
                }
                HomeScreenUiEvents.NavigateUp -> {
                    navController.navigateUp()
                }
                HomeScreenUiEvents.NavigateToSearchRecipesScreen -> {
                    navController.navigate(
                        route = Screen.RecipeListScreen.route + "/ /${
                            withContext(Dispatchers.IO) {
                                URLEncoder.encode(
                                    "https://images.unsplash.com/photo-1484723091739-30a097e8f929?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=449&q=80",
                                    StandardCharsets.UTF_8.toString()
                                )
                            }
                        }/false"
                    )
                }
                HomeScreenUiEvents.NavigateToCategoriesScreen -> {
                    navController.navigate(route = Screen.CategoriesScreen.route)
                }
            }
        }
    }

    BackHandler {
        if (scaffoldState.drawerState.isOpen) {
            viewModel.sendUiEvents(HomeScreenUiEvents.CloseNavDrawer)
        } else {
            onFinishCalled()
        }
    }


    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {

        }
    ) { padding ->
        LazyColumn {
            item(1) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(LocalConfiguration.current.screenHeightDp.dp / 2)
                            .graphicsLayer {
                                shadowElevation = 8.dp.toPx()
                                shape = RectangleMinusSemicircleShape()
                                clip = true
                            }
                            .drawBehind {
                                drawRect(color = Color(0xFF000000))
                            }, contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopCenter)
                                .drawBehind {
                                    drawRect(Color.Transparent)
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,

                            ) {
                            IconButton(
                                onClick = { viewModel.sendUiEvents(HomeScreenUiEvents.OpenNavDrawer) },
                                modifier = Modifier.padding(MyPadding.small)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.MenuOpen,
                                    contentDescription = stringResource(R.string.menu_icon_description),
                                    tint = MaterialTheme.colorScheme.primaryContainer,
                                    modifier = Modifier.size(40.dp)
                                )
                            }

                            IconButton(
                                onClick = { viewModel.sendUiEvents(HomeScreenUiEvents.NavigateToSearchRecipesScreen) },
                                modifier = Modifier.padding(MyPadding.small)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = stringResource(R.string.search_icon_description),
                                    tint = MaterialTheme.colorScheme.primaryContainer,
                                    modifier = Modifier.size(40.dp)
                                )
                            }

                        }

                        SubcomposeAsyncImage(
                            model = "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=481&q=80",
                            loading = {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(50.dp),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            },
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .graphicsLayer {
                                    this.alpha = 0.25f
                                    shadowElevation = 8.dp.toPx()
                                    clip = true
                                },
                            contentScale = ContentScale.Crop,
                            filterQuality = FilterQuality.Medium
                        )
                        Text(
                            text = stringResource(R.string.main_phrase),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.ExtraLight,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            fontFamily = lemonMilkFonts,
                            lineHeight = 40.sp
                        )
                    }
                }
            }
        }
    }
}