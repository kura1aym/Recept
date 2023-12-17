package com.example.recipeapp.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openWebPage(url: String, context: Context) {
    val webpage: Uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, webpage)
    context.startActivity(intent)
}