package com.example.recipeapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.recipeapp.R
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.recipes

@Composable
fun HomeScreen(
    //recipeUiState: RecipeUiState,
    //retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    RecipeListScreen(recipe = recipes, )
//    when (recipeUiState) {
//        is RecipeUiState.Loading -> LoadingScreen(modifier.size(200.dp))
//        is RecipeUiState.Success ->
//            RecipeListScreen(
//                recipe = recipeUiState.recipe,
//                modifier = modifier
//                    .padding(
//                        start = dimensionResource(R.dimen.padding_medium),
//                        top = dimensionResource(R.dimen.padding_medium),
//                        end = dimensionResource(R.dimen.padding_medium)
//                    ),
//                contentPadding = contentPadding
//            )
//        else -> ErrorScreen(retryAction, modifier)
//    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading),
        modifier = modifier
    )
}


@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.loading_failed))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
private fun RecipeListScreen(
    recipe: List<Recipe>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {

    TopRecipeSection()
    Spacer(modifier)
    CategoriesSection()
//    LazyColumn(
//        modifier = modifier,
//        contentPadding = contentPadding,
//        verticalArrangement = Arrangement.spacedBy(24.dp)
//    ) {
//        items(
//            items = recipe,
//            key = { amphibian ->
//                amphibian.name
//            }
//        ) { amphibian ->
//            RecipeCard(amphibian = amphibian, modifier = Modifier.fillMaxSize())
//        }
//    }
}