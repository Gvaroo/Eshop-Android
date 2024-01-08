package com.example.OnlineShop.presenter.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.compose.runtime.remember

import com.example.OnlineShop.presenter.ui.category.Categories
import com.example.OnlineShop.presenter.ui.navbar.BottomBar
import com.example.OnlineShop.presenter.ui.product.ProductScreen
import com.example.OnlineShop.presenter.ui.search.SearchBar
import com.example.OnlineShop.ui.theme.DarkColorScheme
import com.example.OnlineShop.ui.theme.LightColorScheme

import com.example.retrofit.presenter.view_model.AuthViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val viewModel: AuthViewModel = hiltViewModel()
    var mainPageVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val isLoggedIn = viewModel.isLoggedIn()
        if (!isLoggedIn) {
            navController.navigate("loginScreen")
        } else {
            mainPageVisible = true
        }
    }

    if (mainPageVisible) {
        val isDarkMode by rememberUpdatedState(newValue = viewModel.isDarkMode.value)

        val colorScheme = if (isDarkMode == true) DarkColorScheme else LightColorScheme
        MaterialTheme(
            colorScheme = colorScheme,
        ) {
            Scaffold(
                bottomBar = { BottomBar(navController = navController) }
            ) { innerPadding ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = innerPadding.calculateTopPadding(),
                            bottom = innerPadding.calculateBottomPadding()
                        )

                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    SearchBar()
                    Spacer(modifier = Modifier.height(16.dp))
                    Categories()
                    Spacer(modifier = Modifier.height(16.dp))
                    ProductScreen(navController)

                }
            }
        }
    }
}














