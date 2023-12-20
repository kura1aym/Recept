package com.example.recipeapp.ui.screens.recipe_list_screen


import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.example.recipeapp.core.Padding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
//import com.example.recipeapp.core.MyPadding
import com.example.recipeapp.core.Screen
import com.example.recipeapp.core.lemonMilkFonts
import com.example.recipeapp.ui.RectangleMinusSemicircleShape
//import com.example.recipeapp.ui.custom_view.CustomShape
import kotlinx.coroutines.flow.collectLatest
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.format.TextStyle


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RecipeListScreen(
    viewModel: RecipeListViewModel = hiltViewModel(),
    navController: NavHostController,
    windowSize: WindowWidthSizeClass


){
    val state = viewModel.state
    val showSearchBoxState = rememberSaveable { mutableStateOf(false) }
    val searchBoxState = viewModel.searchBoxState.value
    val keyboardController = LocalSoftwareKeyboardController.current
    val isEditModeOn = viewModel.isEditModeOn.value
    val scaffoldState = rememberScaffoldState()
    val refreshState = rememberSwipeRefreshState(isRefreshing = viewModel.isRefreshingState.value)

    LaunchedEffect(key1 = Unit) {
        viewModel.toRecipeListScreenEvents.collectLatest {
            when (it) {
                ToRecipeListScreenEvents.NavigateUp -> navController.navigateUp()
                is ToRecipeListScreenEvents.ShowSnackbar -> scaffoldState.snackbarHostState.showSnackbar(
                    it.message
                )
            }
        }
    }

    BackHandler {
        if (isEditModeOn) {
            viewModel.receiveFromRecipeListScreenEvents(FromRecipeListScreenEvents.DisableEditMode)
        } else {
            viewModel.onEvent(ToRecipeListScreenEvents.NavigateUp)
        }
    }

    if (viewModel.getSavedRecipes.value) {
        SwipeRefresh(state = refreshState, onRefresh = {
            if (viewModel.getSavedRecipes.value) {
                viewModel.receiveFromRecipeListScreenEvents(FromRecipeListScreenEvents.ChangeRefreshState)
                viewModel.onClearSearchBoxButtonClicked()
                viewModel.receiveFromRecipeListScreenEvents(FromRecipeListScreenEvents.ChangeRefreshState)
            }
        }) {
            RecipeListUi(
                windowSize = windowSize,
                scaffoldState = scaffoldState,
                viewModel = viewModel,
                isEditModeOn = isEditModeOn,
                navController = navController,
                showSearchBoxState = showSearchBoxState,
                searchBoxState = searchBoxState,
                keyboardController = keyboardController,
                state = state
            )
        }
    }else{
        RecipeListUi(
            windowSize = windowSize,
            scaffoldState = scaffoldState,
            viewModel = viewModel,
            isEditModeOn = isEditModeOn,
            navController = navController,
            showSearchBoxState = showSearchBoxState,
            searchBoxState = searchBoxState,
            keyboardController = keyboardController,
            state = state
        )
    }
}



@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun RecipeListUi(
    isEditModeOn: Boolean,
    scaffoldState: ScaffoldState,
    viewModel:RecipeListViewModel,
    navController: NavHostController,
    showSearchBoxState: MutableState<Boolean>,
    searchBoxState: String,
    keyboardController: SoftwareKeyboardController?,
    state: RecipeListScreenState,
    windowSize : WindowWidthSizeClass
){
    when (windowSize) {
        WindowWidthSizeClass.Medium -> {
            Scaffold(
                scaffoldState = scaffoldState,
                backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.background
            ) { padding ->
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
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
                                },
                        ) {
                            SubcomposeAsyncImage(
                                model = viewModel.imageUrl.value,
                                loading = {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .padding(50.dp)
                                            .size(50.dp),
                                        color = MaterialTheme.colors.primaryVariant
                                    )
                                },
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .graphicsLayer {
                                        this.alpha = 0.25f
                                        shadowElevation = 8.dp.toPx()
                                        shape = RectangleMinusSemicircleShape()
                                        clip = true
                                    }
                                    .align(Alignment.Center),
                                contentScale = ContentScale.Crop,
                                filterQuality = FilterQuality.Medium
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.TopCenter)
                                    .drawBehind {
                                        drawRect(Color.Transparent)
                                    },
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AnimatedVisibility(visible = isEditModeOn) {
                                    IconButton(
                                        onClick = {
                                            viewModel.receiveFromRecipeListScreenEvents(
                                                FromRecipeListScreenEvents.DeleteButtonClicked
                                            )
                                        },
                                        modifier = Modifier.padding(Padding.small)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Delete,
                                            contentDescription = "goto home screen",
                                            tint = androidx.compose.material3.MaterialTheme.colorScheme.background
                                        )
                                    }
                                }

                                AnimatedVisibility(visible = !isEditModeOn) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        IconButton(
                                            onClick = { navController.navigateUp() },
                                            modifier = Modifier.padding(Padding.small)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.ArrowBack,
                                                contentDescription = "goto home screen",
                                                tint = androidx.compose.material3.MaterialTheme.colorScheme.background,
                                                modifier = Modifier.size(40.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(Padding.medium))
                                        AnimatedVisibility(visible = !showSearchBoxState.value) {
                                            IconButton(onClick = {
                                                showSearchBoxState.value = true
                                            }) {
                                                Icon(
                                                    imageVector = Icons.Default.Search,
                                                    contentDescription = "search recipe",
                                                    tint = androidx.compose.material3.MaterialTheme.colorScheme.background,
                                                    modifier = Modifier.size(40.dp)
                                                )
                                            }
                                        }
                                        AnimatedVisibility(visible = showSearchBoxState.value) {
                                            OutlinedTextField(
                                                value = searchBoxState,
                                                onValueChange = viewModel::onSearchBoxValueChanged,
                                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                                    focusedBorderColor = androidx.compose.material3.MaterialTheme.colorScheme.primary, // Color when the text field is focused
                                                    unfocusedBorderColor = androidx.compose.material3.MaterialTheme.colorScheme.background, // Color when the text field is not focused
                                                    textColor = androidx.compose.material3.MaterialTheme.colorScheme.background, // Text color
                                                    cursorColor = androidx.compose.material3.MaterialTheme.colorScheme.primary // Color of the cursor
                                                ),
                                                modifier = Modifier
                                                    .fillMaxWidth(0.8f),
                                                label = {
                                                    Text(
                                                        "Search ${viewModel.category.value} recipes",
                                                        color = androidx.compose.material3.MaterialTheme.colorScheme.background
                                                    )
                                                },
                                                placeholder = {
                                                    Text(
                                                        "Search ${viewModel.category.value} recipes",
                                                        color = androidx.compose.material3.MaterialTheme.colorScheme.background
                                                    )
                                                },
                                                keyboardActions = KeyboardActions(onSearch = {
                                                    keyboardController?.hide()
                                                }),
                                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
                                            )
                                            Spacer(modifier = Modifier.width(Padding.medium))

                                        }
                                        AnimatedVisibility(visible = showSearchBoxState.value) {
                                            IconButton(
                                                onClick = {
                                                    showSearchBoxState.value = false
                                                    viewModel.onClearSearchBoxButtonClicked()
                                                },
                                                modifier = Modifier.align(Alignment.CenterVertically)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Clear,
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Text(
                                text = viewModel.category.value.ifBlank { "Search across all recipes!" },
                                style = MaterialTheme.typography.h4,
                                fontWeight = FontWeight.ExtraLight,
                                textAlign = TextAlign.Center,
                                color = androidx.compose.material3.MaterialTheme.colorScheme.background,
                                fontFamily = lemonMilkFonts,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }

                    items(state.items.size) { index ->
                        Log.d("HomeScreen", "item size is ${state.items.size} and index is $index")
                        val item = state.items[index]

                        LaunchedEffect(key1 = Unit) {
                            if (index >= state.items.size - 5 && !state.endReached && !state.isLoading) {
                                Log.d("recipelistscreen", "entered if statement")
                                viewModel.loadNextItems()
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(Padding.small)
                                .combinedClickable(onLongClick = {
                                    if (!isEditModeOn && viewModel.getSavedRecipes.value) {
                                        viewModel.isEditModeOn.value = true
                                    }
                                }) {
                                    if (!isEditModeOn) {
                                        val title =
                                            URLEncoder.encode(
                                                item.title,
                                                StandardCharsets.UTF_8.toString()
                                            )
                                        val tag =
                                            URLEncoder.encode(
                                                item.tag,
                                                StandardCharsets.UTF_8.toString()
                                            )
                                        navController.navigate(Screen.RecipeScreen.route + "/${title}/${tag}/${viewModel.getSavedRecipes.value}") {
                                            launchSingleTop = true
                                        }
                                    } else {
                                        viewModel.onSelectRadioButtonClicked(item.title)
                                    }
                                }
                        ) {
                            SubcomposeAsyncImage(
                                model = item.imageUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height((0.8f * 250).dp)
                                    .graphicsLayer {
                                        shape = RoundedCornerShape(Padding.medium)
                                        clip = true
                                    },
                                contentScale = ContentScale.Crop,
                                filterQuality = FilterQuality.Medium,
                                colorFilter = if (isEditModeOn) ColorFilter.tint(
                                    Color.DarkGray,
                                    BlendMode.Multiply
                                ) else null
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    text = item.title,
                                    fontFamily = lemonMilkFonts,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier
                                        .fillMaxWidth(0.7f)
                                )
                                AnimatedVisibility(isEditModeOn) {
                                    RadioButton(
                                        selected = viewModel.recipesToBeDeleted.contains(item.title),
                                        onClick = {
                                            viewModel.onSelectRadioButtonClicked(title = item.title)
                                        })
                                }
                            }
                        }
                    }
                    if (state.isLoading) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colors.primaryVariant,
                                    modifier = Modifier
                                )
                            }
                        }
                   }
                }
            }

        }
        else ->{
            Scaffold(
                scaffoldState = scaffoldState,
                backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.background
            ) { padding ->
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
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
                                },
                        ) {
                            SubcomposeAsyncImage(
                                model = viewModel.imageUrl.value,
                                loading = {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .padding(50.dp)
                                            .size(50.dp),
                                        color = MaterialTheme.colors.primaryVariant
                                    )
                                },
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .graphicsLayer {
                                        this.alpha = 0.25f
                                        shadowElevation = 8.dp.toPx()
                                        shape = RectangleMinusSemicircleShape()
                                        clip = true
                                    }
                                    .align(Alignment.Center),
                                contentScale = ContentScale.Crop,
                                filterQuality = FilterQuality.Medium
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.TopCenter)
                                    .drawBehind {
                                        drawRect(Color.Transparent)
                                    },
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AnimatedVisibility(visible = isEditModeOn) {
                                    IconButton(
                                        onClick = {
                                            viewModel.receiveFromRecipeListScreenEvents(
                                                FromRecipeListScreenEvents.DeleteButtonClicked
                                            )
                                        },
                                        modifier = Modifier.padding(Padding.small)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Delete,
                                            contentDescription = "goto home screen",
                                            tint = androidx.compose.material3.MaterialTheme.colorScheme.background
                                        )
                                    }
                                }

                                AnimatedVisibility(visible = !isEditModeOn) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        IconButton(
                                            onClick = { navController.navigateUp() },
                                            modifier = Modifier.padding(Padding.small)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.ArrowBack,
                                                contentDescription = "goto home screen",
                                                tint = androidx.compose.material3.MaterialTheme.colorScheme.background,
                                                modifier = Modifier.size(40.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(Padding.medium))
                                        AnimatedVisibility(visible = !showSearchBoxState.value) {
                                            IconButton(onClick = {
                                                showSearchBoxState.value = true
                                            }) {
                                                Icon(
                                                    imageVector = Icons.Default.Search,
                                                    contentDescription = "search recipe",
                                                    tint = androidx.compose.material3.MaterialTheme.colorScheme.background,
                                                    modifier = Modifier.size(40.dp)
                                                )
                                            }
                                        }
                                        AnimatedVisibility(visible = showSearchBoxState.value) {
                                            OutlinedTextField(
                                                value = searchBoxState,
                                                onValueChange = viewModel::onSearchBoxValueChanged,
                                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                                    focusedBorderColor = androidx.compose.material3.MaterialTheme.colorScheme.primary, // Color when the text field is focused
                                                    unfocusedBorderColor = androidx.compose.material3.MaterialTheme.colorScheme.background, // Color when the text field is not focused
                                                    textColor = androidx.compose.material3.MaterialTheme.colorScheme.background, // Text color
                                                    cursorColor = androidx.compose.material3.MaterialTheme.colorScheme.primary // Color of the cursor
                                                ),
                                                modifier = Modifier
                                                    .fillMaxWidth(0.8f),
                                                label = {
                                                    Text(
                                                        "Search ${viewModel.category.value} recipes",
                                                        color = androidx.compose.material3.MaterialTheme.colorScheme.background
                                                    )
                                                },
                                                placeholder = {
                                                    Text(
                                                        "Search ${viewModel.category.value} recipes",
                                                        color = androidx.compose.material3.MaterialTheme.colorScheme.background
                                                    )
                                                },
                                                keyboardActions = KeyboardActions(onSearch = {
                                                    keyboardController?.hide()
                                                }),
                                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
                                            )
                                            Spacer(modifier = Modifier.width(Padding.medium))

                                        }
                                        AnimatedVisibility(visible = showSearchBoxState.value) {
                                            IconButton(
                                                onClick = {
                                                    showSearchBoxState.value = false
                                                    viewModel.onClearSearchBoxButtonClicked()
                                                },
                                                modifier = Modifier.align(Alignment.CenterVertically)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Clear,
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Text(
                                text = viewModel.category.value.ifBlank { "Search across all recipes!" },
                                style = MaterialTheme.typography.h4,
                                fontWeight = FontWeight.ExtraLight,
                                textAlign = TextAlign.Center,
                                color = androidx.compose.material3.MaterialTheme.colorScheme.background,
                                fontFamily = lemonMilkFonts,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }

                    items(state.items.size) { index ->
                        Log.d("HomeScreen", "item size is ${state.items.size} and index is $index")
                        val item = state.items[index]

                        LaunchedEffect(key1 = Unit) {
                            if (index >= state.items.size - 5 && !state.endReached && !state.isLoading) {
                                Log.d("recipelistscreen", "entered if statement")
                                viewModel.loadNextItems()
                            }
                        }

                            Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(Padding.small)
                                .combinedClickable(onLongClick = {
                                    if (!isEditModeOn && viewModel.getSavedRecipes.value) {
                                        viewModel.isEditModeOn.value = true
                                    }
                                }) {
                                    if (!isEditModeOn) {
                                        val title =
                                            URLEncoder.encode(
                                                item.title,
                                                StandardCharsets.UTF_8.toString()
                                            )
                                        val tag =
                                            URLEncoder.encode(
                                                item.tag,
                                                StandardCharsets.UTF_8.toString()
                                            )
                                        navController.navigate(Screen.RecipeScreen.route + "/${title}/${tag}/${viewModel.getSavedRecipes.value}") {
                                            launchSingleTop = true
                                        }
                                    } else {
                                       viewModel.onSelectRadioButtonClicked(item.title)
                                    }
                                }
                        ) {
                                Spacer(modifier = Modifier.width(40.dp))
                            SubcomposeAsyncImage(
                                model = item.imageUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .width(500.dp)
                                    .height(200.dp)
                                    .graphicsLayer {
                                        shape = RoundedCornerShape(Padding.medium)
                                        clip = true
                                    },
                                contentScale = ContentScale.Crop,
                                filterQuality = FilterQuality.Medium,
                                colorFilter = if (isEditModeOn) ColorFilter.tint(
                                    Color.DarkGray,
                                    BlendMode.Multiply
                                ) else null
                            )
                                Spacer(modifier = Modifier.width(40.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    text = item.title,
                                    fontFamily = lemonMilkFonts,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier
                                      .width(500.dp),

                                    style = androidx.compose.ui.text.TextStyle(
                                        fontSize = 35.sp // Set the desired font size here
                                    )
                                )
                                AnimatedVisibility(isEditModeOn) {
                                    RadioButton(
                                        selected = viewModel.recipesToBeDeleted.contains(item.title),
                                        onClick = {
                                            viewModel.onSelectRadioButtonClicked(title = item.title)
                                        })
                                }
                            }
                        }
                    }
                    if (state.isLoading) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colors.primaryVariant,
                                    modifier = Modifier
                                )
                            }
                        }
                    }
                }
            }

    }
}}