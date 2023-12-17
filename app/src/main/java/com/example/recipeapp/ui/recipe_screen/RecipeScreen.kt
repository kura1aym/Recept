package com.example.recipeapp.ui.recipe_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DownloadForOffline
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.recipeapp.ui.theme.MyPadding
import com.example.recipeapp.ui.theme.lemonMilkFonts

@Composable
fun RecipeScreen(){



    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState){padding->
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(LocalConfiguration.current.screenHeightDp.dp / 2)
                        .graphicsLayer {
                            shadowElevation = 8.dp.toPx()
                            //  shape = CustomShape()
                            clip = true
                        }
                        .drawBehind {
                            drawRect(color = Color(0xFF000000))
                        },
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .drawBehind { drawRect(color = Color.Transparent) },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = { },
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "goto home screen",
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            IconButton(onClick = {
                            }) {
                                Icon(imageVector = Icons.Default.DownloadForOffline,
                                    contentDescription = "download recipe")
                            }

                            IconButton(
                                onClick ={}
                            )  {

                            }
                        }
                    }

                    SubcomposeAsyncImage(
                        model ="",
                        loading = {
                            CircularProgressIndicator(
                                modifier = Modifier.size(50.dp),
                                color = MaterialTheme.colors.primaryVariant
                            )
                        },
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                this.alpha = 0.25f
                                shadowElevation = 8.dp.toPx()
                                clip = true
                            }
                            .align(Alignment.Center),
                        contentScale = ContentScale.Crop,
                        filterQuality = FilterQuality.Medium
                    )
                    Text(
                        text = "",
                        style = MaterialTheme.typography.h4,
                        fontWeight = FontWeight.ExtraLight,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.onSurface,
                        fontFamily = lemonMilkFonts,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
//                        Spacer(modifier = Modifier.height(MyPadding.medium))
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = "Download Ingredients",
                        fontFamily = lemonMilkFonts,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(horizontal = MyPadding.medium)
                            .clickable{

                            }
                    )
                    Spacer(modifier = Modifier.height(MyPadding.medium))
                    IconButton(onClick = {
                    }) {
                        Icon(imageVector = Icons.Default.DownloadForOffline,
                            contentDescription = "download recipe")
                    }
                }
                Spacer(modifier = Modifier.height(MyPadding.medium))
            }



            item {
                Text(
                    text = "Ingredients",
                    fontFamily = lemonMilkFonts,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(horizontal = MyPadding.medium)
                )
                Spacer(modifier = Modifier.height(MyPadding.medium))
            }



            item {
                Text(
                    text = "Method",
                    fontFamily = lemonMilkFonts,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(horizontal = MyPadding.medium)
                )
                Spacer(modifier = Modifier.height(MyPadding.medium))
            }


        }
    }
}

