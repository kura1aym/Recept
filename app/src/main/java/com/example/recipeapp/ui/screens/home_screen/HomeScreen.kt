package com.example.recipeapp.ui.screens.home_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuOpen
import androidx.compose.material.icons.filled.ContactPage
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.example.recipeapp.R
import com.example.recipeapp.core.Padding
import com.example.recipeapp.core.Screen
import com.example.recipeapp.ui.RectangleMinusSemicircleShape
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
    windowSize: WindowWidthSizeClass,
    onFinishCalled: () -> Unit,
) {
    val popularPicksState by viewModel.popularPicks
    val categoriesState by viewModel.categoriesState
    val scaffoldState = rememberScaffoldState()
    val url = URLEncoder.encode(
        "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=481&q=80",
        StandardCharsets.UTF_8.toString()
    )
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

    when (windowSize) {
        WindowWidthSizeClass.Medium -> {
            Scaffold(
                scaffoldState = scaffoldState,
                backgroundColor = MaterialTheme.colorScheme.background,
            ) { padding ->
                Row(modifier = Modifier
                    .fillMaxSize()){
                    Column(modifier = Modifier
                        .fillMaxHeight()
                        .width(200.dp)
                        .background(MaterialTheme.colorScheme.tertiary)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ingredients),
                            contentDescription = null,
                            modifier = Modifier
                                .height(200.dp)
                                .width(300.dp),
                            contentScale = ContentScale.Fit,
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(Screen.RecipeListScreen.route + "/saved/$url/true")
                                }
                                .padding(Padding.medium), verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { navController.navigate(Screen.RecipeListScreen.route + "/saved/$url/true") }) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = stringResource(R.string.saved_recipes),
                                    tint = MaterialTheme.colorScheme.onTertiary
                                )
                            }
                            Spacer(modifier = Modifier.width(Padding.small))
                            Text(text = stringResource(R.string.favourites_recipes), color = MaterialTheme.colorScheme.onTertiary)
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.sendUiEvents(HomeScreenUiEvents.NavigateToCategoriesScreen)
                                }
                                .padding(Padding.medium), verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = {
                                viewModel.sendUiEvents(
                                    HomeScreenUiEvents.NavigateToCategoriesScreen
                                )
                            }) {
                                Icon(
                                    imageVector = Icons.Default.More,
                                    contentDescription = stringResource(R.string.saved_recipes),
                                    tint = MaterialTheme.colorScheme.onTertiary
                                )
                            }
                            Spacer(modifier = Modifier.width(Padding.small))
                            Text(text = stringResource(R.string.categories), color = MaterialTheme.colorScheme.onTertiary)
                        }
                    }
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
                                            onClick = { viewModel.sendUiEvents(HomeScreenUiEvents.NavigateToSearchRecipesScreen) },
                                            modifier = Modifier.padding(Padding.small)
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
                        item(2) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = stringResource(R.string.popular_recipes),
                                    fontFamily = lemonMilkFonts,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(Padding.medium),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = androidx.compose.material.MaterialTheme.typography.h5
                                )
                            }
                        }
                        item(3) {
                            when {
                                popularPicksState.loading -> {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(color = androidx.compose.material.MaterialTheme.colors.primaryVariant)
                                    }
                                }
                                popularPicksState.error.isNotBlank() -> {
                                    Text(text = popularPicksState.error,
                                        color = Color.Yellow,
                                        modifier = Modifier.padding(horizontal = Padding.medium))
                                }
                                else -> {
                                    LazyRow(verticalAlignment = Alignment.CenterVertically) {
                                        items(popularPicksState.recipes) { item ->
                                            Column(
                                                modifier = Modifier
                                                    .width(250.dp)
                                                    .height(170.dp)
                                                    .padding(horizontal = Padding.medium)
                                                    .clickable {
                                                        navController.navigate(Screen.RecipeScreen.route + "/${item.title}/${item.tag}/false") {
                                                            launchSingleTop = true
                                                        }
                                                    }
                                                    .background(
                                                        color = MaterialTheme.colorScheme.primary,
                                                        shape = RoundedCornerShape(8.dp)
                                                    )
                                            )
                                            {
                                                SubcomposeAsyncImage(
                                                    model = item.imageUrl,
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .fillMaxHeight(0.6f)
                                                        .graphicsLayer {
                                                            shape = RoundedCornerShape(Padding.medium)
                                                            clip = true
                                                        }
                                                        .clickable {
                                                            navController.navigate(Screen.RecipeScreen.route + "/${item.title}/${item.tag}/false") {
                                                                launchSingleTop = true
                                                            }
                                                        },
                                                    contentScale = ContentScale.Crop,
                                                    loading = {
                                                        androidx.compose.material.CircularProgressIndicator(
                                                            modifier = Modifier.size(20.dp),
                                                            color = androidx.compose.material.MaterialTheme.colors.primaryVariant
                                                        )
                                                    },
                                                    filterQuality = FilterQuality.Medium,
                                                )
                                                Spacer(modifier = Modifier.width(Padding.small))
                                                Text(
                                                    text = item.title,
                                                    fontFamily = lemonMilkFonts,
                                                    fontWeight = FontWeight.Normal,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .fillMaxHeight()
                                                )
                                                Spacer(modifier = Modifier.width(Padding.small))
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(Padding.medium))
                                }
                            }
                        }
                        item(4) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = Padding.small)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically,) {
                                    androidx.compose.material.Text(
                                        text = stringResource(R.string.categories),
                                        fontFamily = lemonMilkFonts,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(Padding.medium/2),
                                        style = androidx.compose.material.MaterialTheme.typography.h5
                                    )
                                }

                                androidx.compose.material.Text(
                                    text = stringResource(R.string.view_all),
                                    modifier = Modifier
                                        .padding(Padding.medium)
                                        .clickable {
                                            viewModel.sendUiEvents(HomeScreenUiEvents.NavigateToCategoriesScreen)
                                        },
                                    fontFamily = lemonMilkFonts,
                                    fontWeight = FontWeight.Medium,
                                    style = androidx.compose.material.MaterialTheme.typography.h6,
                                )
                            }
                        }

                        item(5) {
                            when {
                                categoriesState.isLoading -> {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(color = androidx.compose.material.MaterialTheme.colors.primaryVariant)
                                    }
                                }
                                categoriesState.error.isNotBlank() -> {
                                    Text(text = categoriesState.error,
                                        color = Color.Yellow,
                                        modifier = Modifier.padding(horizontal = Padding.medium))
                                }
                                else -> {
                                    LazyRow(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center) {
                                        items(categoriesState.categories) { item ->
                                            Column(
                                                modifier = Modifier
                                                    .width(250.dp)
                                                    .height(170.dp)
                                                    .padding(horizontal = Padding.medium)
                                                    .clickable {
                                                        navController.navigate(
                                                            route = Screen.RecipeListScreen.route + "/${item.category}/${
                                                                URLEncoder.encode(
                                                                    item.imageUrl,
                                                                    StandardCharsets.UTF_8.toString()
                                                                )
                                                            }/false"
                                                        ) { launchSingleTop = true }
                                                    }
                                                    .background(
                                                        color = MaterialTheme.colorScheme.primary,
                                                        shape = RoundedCornerShape(8.dp)
                                                    )
                                            )
                                            {
                                                SubcomposeAsyncImage(
                                                    model = item.imageUrl,
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .fillMaxHeight(0.6f)
                                                        .graphicsLayer {
                                                            shape = RoundedCornerShape(Padding.medium)
                                                            clip = true
                                                        },
                                                    contentScale = ContentScale.Crop,
                                                    loading = {
                                                        CircularProgressIndicator(
                                                            modifier = Modifier.size(20.dp),
                                                            color = androidx.compose.material.MaterialTheme.colors.primaryVariant
                                                        )
                                                    },
                                                    filterQuality = FilterQuality.Medium,
                                                )
                                                Spacer(modifier = Modifier.width(Padding.small))
                                                Text(
                                                    text = item.category,
                                                    fontFamily = lemonMilkFonts,
                                                    fontWeight = FontWeight.Normal,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .fillMaxHeight()
                                                )
                                                Spacer(modifier = Modifier.width(Padding.small))
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(Padding.medium))
                                }
                            }
                        }
                    }
                }
            }
        }
        WindowWidthSizeClass.Expanded -> {
            Scaffold(
                scaffoldState = scaffoldState,
                backgroundColor = MaterialTheme.colorScheme.background,
            ) { padding ->
                Row(modifier = Modifier
                    .fillMaxSize()){
                    Column(modifier = Modifier
                        .fillMaxHeight()
                        .width(200.dp)
                        .background(MaterialTheme.colorScheme.tertiary)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ingredients),
                            contentDescription = null,
                            modifier = Modifier
                                .height(200.dp)
                                .width(300.dp),
                            contentScale = ContentScale.Fit,
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(Screen.RecipeListScreen.route + "/saved/$url/true")
                                }
                                .padding(Padding.medium), verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { navController.navigate(Screen.RecipeListScreen.route + "/saved/$url/true") }) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = stringResource(R.string.saved_recipes),
                                    tint = MaterialTheme.colorScheme.onTertiary
                                )
                            }
                            Spacer(modifier = Modifier.width(Padding.small))
                            Text(text = stringResource(R.string.favourites_recipes), color = MaterialTheme.colorScheme.onTertiary)
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.sendUiEvents(HomeScreenUiEvents.NavigateToCategoriesScreen)
                                }
                                .padding(Padding.medium), verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = {
                                viewModel.sendUiEvents(
                                    HomeScreenUiEvents.NavigateToCategoriesScreen
                                )
                            }) {
                                Icon(
                                    imageVector = Icons.Default.More,
                                    contentDescription = stringResource(R.string.saved_recipes),
                                    tint = MaterialTheme.colorScheme.onTertiary
                                )
                            }
                            Spacer(modifier = Modifier.width(Padding.small))
                            Text(text = stringResource(R.string.categories), color = MaterialTheme.colorScheme.onTertiary)
                        }
                    }
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
                                            onClick = { viewModel.sendUiEvents(HomeScreenUiEvents.NavigateToSearchRecipesScreen) },
                                            modifier = Modifier.padding(Padding.small)
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
                        item(2) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = stringResource(R.string.popular_recipes),
                                    fontFamily = lemonMilkFonts,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(Padding.medium),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = androidx.compose.material.MaterialTheme.typography.h5
                                )
                            }
                        }
                        item(3) {
                            when {
                                popularPicksState.loading -> {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(color = androidx.compose.material.MaterialTheme.colors.primaryVariant)
                                    }
                                }
                                popularPicksState.error.isNotBlank() -> {
                                    Text(text = popularPicksState.error,
                                        color = Color.Yellow,
                                        modifier = Modifier.padding(horizontal = Padding.medium))
                                }
                                else -> {
                                    LazyRow(verticalAlignment = Alignment.CenterVertically) {
                                        items(popularPicksState.recipes) { item ->
                                            Column(
                                                modifier = Modifier
                                                    .width(250.dp)
                                                    .height(170.dp)
                                                    .padding(horizontal = Padding.medium)
                                                    .clickable {
                                                        navController.navigate(Screen.RecipeScreen.route + "/${item.title}/${item.tag}/false") {
                                                            launchSingleTop = true
                                                        }
                                                    }
                                                    .background(
                                                        color = MaterialTheme.colorScheme.background,
                                                        shape = RoundedCornerShape(8.dp)
                                                    )
                                            )
                                            {
                                                SubcomposeAsyncImage(
                                                    model = item.imageUrl,
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .fillMaxHeight(0.6f)
                                                        .graphicsLayer {
                                                            shape = RoundedCornerShape(Padding.medium)
                                                            clip = true
                                                        }
                                                        .clickable {
                                                            navController.navigate(Screen.RecipeScreen.route + "/${item.title}/${item.tag}/false") {
                                                                launchSingleTop = true
                                                            }
                                                        },
                                                    contentScale = ContentScale.Crop,
                                                    loading = {
                                                        androidx.compose.material.CircularProgressIndicator(
                                                            modifier = Modifier.size(20.dp),
                                                            color = androidx.compose.material.MaterialTheme.colors.primaryVariant
                                                        )
                                                    },
                                                    filterQuality = FilterQuality.Medium,
                                                )
                                                Spacer(modifier = Modifier.width(Padding.small))
                                                Text(
                                                    text = item.title,
                                                    fontFamily = lemonMilkFonts,
                                                    fontWeight = FontWeight.Normal,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .fillMaxHeight()
                                                )
                                                Spacer(modifier = Modifier.width(Padding.small))
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(Padding.medium))
                                }
                            }
                        }
                        item(4) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = Padding.small)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically,) {
                                    androidx.compose.material.Text(
                                        text = stringResource(R.string.categories),
                                        fontFamily = lemonMilkFonts,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(Padding.medium/2),
                                        style = androidx.compose.material.MaterialTheme.typography.h5
                                    )
                                }

                                androidx.compose.material.Text(
                                    text = stringResource(R.string.view_all),
                                    modifier = Modifier
                                        .padding(Padding.medium)
                                        .clickable {
                                            viewModel.sendUiEvents(HomeScreenUiEvents.NavigateToCategoriesScreen)
                                        },
                                    fontFamily = lemonMilkFonts,
                                    fontWeight = FontWeight.Medium,
                                    style = androidx.compose.material.MaterialTheme.typography.h6,
                                )
                            }
                        }

                        item(5) {
                            when {
                                categoriesState.isLoading -> {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(color = androidx.compose.material.MaterialTheme.colors.primaryVariant)
                                    }
                                }
                                categoriesState.error.isNotBlank() -> {
                                    Text(text = categoriesState.error,
                                        color = Color.Yellow,
                                        modifier = Modifier.padding(horizontal = Padding.medium))
                                }
                                else -> {
                                    LazyRow(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center) {
                                        items(categoriesState.categories) { item ->
                                            Column(
                                                modifier = Modifier
                                                    .width(250.dp)
                                                    .height(170.dp)
                                                    .padding(horizontal = Padding.medium)
                                                    .clickable {
                                                        navController.navigate(
                                                            route = Screen.RecipeListScreen.route + "/${item.category}/${
                                                                URLEncoder.encode(
                                                                    item.imageUrl,
                                                                    StandardCharsets.UTF_8.toString()
                                                                )
                                                            }/false"
                                                        ) { launchSingleTop = true }
                                                    }
                                                    .background(
                                                        color = MaterialTheme.colorScheme.background,
                                                        shape = RoundedCornerShape(8.dp)
                                                    )
                                            )
                                            {
                                                SubcomposeAsyncImage(
                                                    model = item.imageUrl,
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .fillMaxHeight(0.6f)
                                                        .graphicsLayer {
                                                            shape = RoundedCornerShape(Padding.medium)
                                                            clip = true
                                                        },
                                                    contentScale = ContentScale.Crop,
                                                    loading = {
                                                        CircularProgressIndicator(
                                                            modifier = Modifier.size(20.dp),
                                                            color = androidx.compose.material.MaterialTheme.colors.primaryVariant
                                                        )
                                                    },
                                                    filterQuality = FilterQuality.Medium,
                                                )
                                                Spacer(modifier = Modifier.width(Padding.small))
                                                Text(
                                                    text = item.category,
                                                    fontFamily = lemonMilkFonts,
                                                    fontWeight = FontWeight.Normal,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .fillMaxHeight()
                                                )
                                                Spacer(modifier = Modifier.width(Padding.small))
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(Padding.medium))
                                }
                            }
                        }
                    }
                }
            }
        }
        else -> {
            Scaffold(
                scaffoldState = scaffoldState,
                backgroundColor = MaterialTheme.colorScheme.background,
                drawerContent = {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.tertiary)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ingredients),
                            contentDescription = null,
                            modifier = Modifier
                                .height(200.dp)
                                .width(300.dp),
                            contentScale = ContentScale.Fit,
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(Screen.RecipeListScreen.route + "/saved/$url/true")
                                }
                                .padding(Padding.medium), verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { navController.navigate(Screen.RecipeListScreen.route + "/saved/$url/true") }) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = stringResource(R.string.saved_recipes),
                                    tint = MaterialTheme.colorScheme.onTertiary
                                )
                            }
                            Spacer(modifier = Modifier.width(Padding.small))
                            Text(text = stringResource(R.string.favourites_recipes), color = MaterialTheme.colorScheme.onTertiary)
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.sendUiEvents(HomeScreenUiEvents.NavigateToCategoriesScreen)
                                }
                                .padding(Padding.medium), verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = {
                                viewModel.sendUiEvents(
                                    HomeScreenUiEvents.NavigateToCategoriesScreen
                                )
                            }) {
                                Icon(
                                    imageVector = Icons.Default.More,
                                    contentDescription = stringResource(R.string.saved_recipes),
                                    tint = MaterialTheme.colorScheme.onTertiary
                                )
                            }
                            Spacer(modifier = Modifier.width(Padding.small))
                            Text(text = stringResource(R.string.categories), color = MaterialTheme.colorScheme.onTertiary)
                        }
                    }
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
                                        modifier = Modifier.padding(Padding.small)
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
                                        modifier = Modifier.padding(Padding.small)
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
                    item(2) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = stringResource(R.string.popular_recipes),
                                fontFamily = lemonMilkFonts,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(Padding.medium),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = androidx.compose.material.MaterialTheme.typography.h5
                            )
                        }
                    }
                    item(3) {
                        when {
                            popularPicksState.loading -> {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(color = androidx.compose.material.MaterialTheme.colors.primaryVariant)
                                }
                            }
                            popularPicksState.error.isNotBlank() -> {
                                Text(text = popularPicksState.error,
                                    color = Color.Yellow,
                                    modifier = Modifier.padding(horizontal = Padding.medium))
                            }
                            else -> {
                                LazyRow(verticalAlignment = Alignment.CenterVertically) {
                                    items(popularPicksState.recipes) { item ->
                                        Column(
                                            modifier = Modifier
                                                .width(250.dp)
                                                .height(170.dp)
                                                .padding(horizontal = Padding.medium)
                                                .clickable {
                                                    navController.navigate(Screen.RecipeScreen.route + "/${item.title}/${item.tag}/false") {
                                                        launchSingleTop = true
                                                    }
                                                }
                                                .background(
                                                    color = MaterialTheme.colorScheme.background,
                                                    shape = RoundedCornerShape(8.dp)
                                                )
                                        )
                                        {
                                            SubcomposeAsyncImage(
                                                model = item.imageUrl,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .fillMaxHeight(0.6f)
                                                    .graphicsLayer {
                                                        shape = RoundedCornerShape(Padding.medium)
                                                        clip = true
                                                    }
                                                    .clickable {
                                                        navController.navigate(Screen.RecipeScreen.route + "/${item.title}/${item.tag}/false") {
                                                            launchSingleTop = true
                                                        }
                                                    },
                                                contentScale = ContentScale.Crop,
                                                loading = {
                                                    androidx.compose.material.CircularProgressIndicator(
                                                        modifier = Modifier.size(20.dp),
                                                        color = androidx.compose.material.MaterialTheme.colors.primaryVariant
                                                    )
                                                },
                                                filterQuality = FilterQuality.Medium,
                                            )
                                            Spacer(modifier = Modifier.width(Padding.small))
                                            Text(
                                                text = item.title,
                                                fontFamily = lemonMilkFonts,
                                                fontWeight = FontWeight.Normal,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .fillMaxHeight()
                                            )
                                            Spacer(modifier = Modifier.width(Padding.small))
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(Padding.medium))
                            }
                        }
                    }
                    item(4) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Padding.small)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically,) {
                                androidx.compose.material.Text(
                                    text = stringResource(R.string.categories),
                                    fontFamily = lemonMilkFonts,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(Padding.medium/2),
                                    style = androidx.compose.material.MaterialTheme.typography.h5
                                )
                            }

                            androidx.compose.material.Text(
                                text = stringResource(R.string.view_all),
                                modifier = Modifier
                                    .padding(Padding.medium)
                                    .clickable {
                                        viewModel.sendUiEvents(HomeScreenUiEvents.NavigateToCategoriesScreen)
                                    },
                                fontFamily = lemonMilkFonts,
                                fontWeight = FontWeight.Medium,
                                style = androidx.compose.material.MaterialTheme.typography.h6,
                            )
                        }
                    }

                    item(5) {
                        when {
                            categoriesState.isLoading -> {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(color = androidx.compose.material.MaterialTheme.colors.primaryVariant)
                                }
                            }
                            categoriesState.error.isNotBlank() -> {
                                Text(text = categoriesState.error,
                                    color = Color.Yellow,
                                    modifier = Modifier.padding(horizontal = Padding.medium))
                            }
                            else -> {
                                LazyRow(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center) {
                                    items(categoriesState.categories) { item ->
                                        Column(
                                            modifier = Modifier
                                                .width(250.dp)
                                                .height(170.dp)
                                                .padding(horizontal = Padding.medium)
                                                .clickable {
                                                    navController.navigate(
                                                        route = Screen.RecipeListScreen.route + "/${item.category}/${
                                                            URLEncoder.encode(
                                                                item.imageUrl,
                                                                StandardCharsets.UTF_8.toString()
                                                            )
                                                        }/false"
                                                    ) { launchSingleTop = true }
                                                }
                                                .background(
                                                    color = MaterialTheme.colorScheme.background,
                                                    shape = RoundedCornerShape(8.dp)
                                                )
                                        )
                                        {
                                            SubcomposeAsyncImage(
                                                model = item.imageUrl,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .fillMaxHeight(0.6f)
                                                    .graphicsLayer {
                                                        shape = RoundedCornerShape(Padding.medium)
                                                        clip = true
                                                    },
                                                contentScale = ContentScale.Crop,
                                                loading = {
                                                    CircularProgressIndicator(
                                                        modifier = Modifier.size(20.dp),
                                                        color = androidx.compose.material.MaterialTheme.colors.primaryVariant
                                                    )
                                                },
                                                filterQuality = FilterQuality.Medium,
                                            )
                                            Spacer(modifier = Modifier.width(Padding.small))
                                            Text(
                                                text = item.category,
                                                fontFamily = lemonMilkFonts,
                                                fontWeight = FontWeight.Normal,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .fillMaxHeight()
                                            )
                                            Spacer(modifier = Modifier.width(Padding.small))
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(Padding.medium))
                            }
                        }
                    }
                }
            }
        }
    }
}