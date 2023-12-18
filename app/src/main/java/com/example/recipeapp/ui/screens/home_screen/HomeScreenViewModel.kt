package com.example.recipeapp.ui.screens.home_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.core.Resource
import com.example.recipeapp.domain.repository.RecipeRepository
import com.example.recipeapp.ui.components.ComponentCategoriesState
import com.example.recipeapp.ui.components.ComponentPopularPicksState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {
    private val popularPicksState =
        mutableStateOf<ComponentPopularPicksState>(ComponentPopularPicksState())
    val popularPicks = popularPicksState as State<ComponentPopularPicksState>

    private val _uiEvents = Channel<HomeScreenUiEvents>()
        val uiEvents = _uiEvents.receiveAsFlow()


        init {
            viewModelScope.launch {
                async { loadPopularPicks(fetchFromRemote = false)
                }
            }
        }

        private suspend fun loadPopularPicks(fetchFromRemote: Boolean) {
            when (val recipeState = recipeRepository.getFirstFourRecipes(fetchFromRemote = fetchFromRemote)) {
                is Resource.Error -> {
                    val errorMessage = recipeState.error?.toString() ?: "recipes"
                    popularPicksState.value = popularPicksState.value.copy(error = errorMessage, loading = false)
                }
                is Resource.Loading -> {
                    popularPicksState.value = popularPicksState.value.copy(error = "", loading = true)
                }
                is Resource.Success -> {
                    popularPicksState.value = popularPicksState.value.copy(
                        error = "",
                        loading = false,
                        recipes = recipeState.data!!
                    )
                }
            }
        }

    fun sendUiEvents(event: HomeScreenUiEvents){
            viewModelScope.launch {
                when(event){
                    HomeScreenUiEvents.CloseNavDrawer -> {
                        _uiEvents.send(HomeScreenUiEvents.CloseNavDrawer)
                    }
                    HomeScreenUiEvents.OpenNavDrawer -> {
                        _uiEvents.send(HomeScreenUiEvents.OpenNavDrawer)
                    }
                    HomeScreenUiEvents.NavigateUp -> {
                        _uiEvents.send(HomeScreenUiEvents.NavigateUp)
                    }
                    HomeScreenUiEvents.NavigateToSearchRecipesScreen -> {
                        _uiEvents.send(HomeScreenUiEvents.NavigateToSearchRecipesScreen)
                    }
                    HomeScreenUiEvents.NavigateToCategoriesScreen -> {
                        _uiEvents.send(HomeScreenUiEvents.NavigateToCategoriesScreen)
                    }
                }
            }
        }
    }

    sealed interface HomeScreenUiEvents{
        object CloseNavDrawer: HomeScreenUiEvents
        object OpenNavDrawer: HomeScreenUiEvents
        object NavigateUp: HomeScreenUiEvents
        object NavigateToSearchRecipesScreen: HomeScreenUiEvents
        object NavigateToCategoriesScreen: HomeScreenUiEvents
    }