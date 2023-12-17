//package com.example.recipeapp.ui.screens
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.FilterQuality
//import androidx.compose.ui.graphics.graphicsLayer
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import coil.compose.SubcomposeAsyncImage
//import com.example.recipeapp.ui.theme.MyPadding
//import com.example.recipeapp.ui.theme.lemonMilkFonts
//import com.example.recipeapp.model.categoryList
//import java.net.URLEncoder
//import java.nio.charset.StandardCharsets
//
//
//@Preview
//@Composable
//fun  CategoriesSection() {
//    Column {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = MyPadding.small)
//        ) {
//            Row(verticalAlignment = Alignment.CenterVertically,) {
//                Text(
//                    text = "Categories",
//                    fontFamily = lemonMilkFonts,
//                    fontWeight = FontWeight.Medium,
//                    modifier = Modifier.padding(MyPadding.medium),
//                    fontSize = 24.sp
//                )
//            }
//            Text(
//                text = "View All",
//                modifier = Modifier
//                    .padding(MyPadding.medium)
//                    .clickable {
//                        //viewModel.sendUiEvents(HomeScreenUiEvents.NavigateToCategoriesScreen)
//                    },
//                fontFamily = lemonMilkFonts,
//                fontWeight = FontWeight.Medium,
//                fontSize = 18.sp
//            )
//        }
//
//        LazyRow {
//            items(categoryList.size) {
//                CategoryItem(it)
//            }
//        }
//    }
//}
//
//@Composable
//fun CategoryItem(
//    index: Int
//) {
//    val finance = categoryList[index]
//    var lastPaddingEnd = 0.dp
//    if (index == categoryList.size - 1) {
//        lastPaddingEnd = 16.dp
//    }
//
//
//}
