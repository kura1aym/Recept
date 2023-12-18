package com.example.recipeapp.domain.pagination

interface Paginator <Key, Item> {
    suspend fun loadNextItems()
    suspend fun reset()
}