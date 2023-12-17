package com.example.recipeapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.recipeapp.R
import com.example.recipeapp.model.recipes
import com.example.recipeapp.ui.theme.MyPadding
import com.example.recipeapp.ui.theme.lemonMilkFonts

fun getGradient(
    startColor: Color,
    endColor: Color,
): Brush {
    return Brush.horizontalGradient(
        colors = listOf(startColor, endColor)
    )
}

@Preview
@Composable
fun TopRecipeSection() {
//    Text(
//        text = "Top Recipes",
//        fontFamily = lemonMilkFonts,
//        fontWeight = FontWeight.Medium,
//        modifier = Modifier.padding(MyPadding.medium),
//        fontSize = 24.sp
//    )
    LazyRow {
        items(recipes.size) { index ->
            RecipeItem(index)
        }
    }
}


@Composable
fun RecipeItem(
    index: Int
) {
    val recipe = recipes[index]
    var lastItemPaddingEnd = 0.dp
    if (index == recipes.size - 1) {
        lastItemPaddingEnd = 16.dp
    }

    Card(
        modifier = Modifier
            .padding(start = 16.dp, end = lastItemPaddingEnd)
            .width(250.dp)
            .height(350.dp)
            .clip(RoundedCornerShape(25.dp))
            .clickable {}
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(25.dp))
        ) {

            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(recipe.imageUrl.firstOrNull())
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img)
            )

            Column(
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.onSecondary
                    )
                )
                Text(
                    text = recipe.category,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}
