package com.example.OnlineShop.presenter.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = "mainScreen",
        title = "Home",
        icon = Icons.Default.Home
    )
    object Cart : BottomBarScreen(
        route = "cartScreen",
        title = "Cart",
        icon = Icons.Default.ShoppingCart
    )

    object Profile : BottomBarScreen(
        route = "profileScreen",
        title = "Profile",
        icon = Icons.Default.Person
    )


    object Settings : BottomBarScreen(
        route = "settingsScreen",
        title = "Settings",
        icon = Icons.Default.Settings
    )
}