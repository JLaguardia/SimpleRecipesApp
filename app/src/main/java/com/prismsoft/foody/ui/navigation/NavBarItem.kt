package com.prismsoft.foody.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavBarItem(
    val title: String,
    val icon: ImageVector,
    val navRoute: String
){
    object Recipes: NavBarItem(
        title = "Recipes",
        icon = Icons.Default.Home,
        "home"
    )

    object Favorites: NavBarItem(
        title = "Recipes",
        icon = Icons.Default.Favorite,
        "home"
    )
    object Joke: NavBarItem(
        title = "Joke",
        icon = Icons.Default.Face,
        "home"
    )
}