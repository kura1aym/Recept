//package com.example.recipeapp.ui
//
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import com.example.recipeapp.ui.screens.BottomNavigationBar
//import com.example.recipeapp.ui.screens.HomeScreen
//import com.example.recipeapp.ui.screens.RecipeViewModel
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun RecipeApp() {
//    Scaffold(
//        modifier = Modifier.fillMaxSize(),
//        bottomBar = {
//            BottomNavigationBar()
//        }
//    ) {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//        ) {
////            val recipeViewModel: RecipeViewModel =
////              viewModel(factory = RecipeViewModel.Factory)
//            HomeScreen(
//                //recipeUiState = recipeViewModel.recipeUiState,
//                modifier = Modifier.fillMaxSize(),
//                contentPadding = it
//            )
//        }
//    }
//}