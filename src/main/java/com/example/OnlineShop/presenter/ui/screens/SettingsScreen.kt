package com.example.OnlineShop.presenter.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.OnlineShop.presenter.ui.navbar.BottomBar
import com.example.OnlineShop.ui.theme.DarkColorScheme
import com.example.OnlineShop.ui.theme.LightColorScheme
import com.example.retrofit.presenter.view_model.AuthViewModel
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    val viewModel: AuthViewModel = hiltViewModel()
 val isDarkMode by rememberUpdatedState(newValue = viewModel.isDarkMode.value)
 val colorScheme = if (isDarkMode == true) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
    ) {
        Scaffold(
            bottomBar = { BottomBar(navController = navController) },
            topBar = {
                TopAppBar(
                    title = { Text(text = "Settings") },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.navigate("mainScreen") }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = it.calculateTopPadding(),
                            bottom = it.calculateBottomPadding()
                        )
                        .verticalScroll(rememberScrollState()),

                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Dark Mode", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Switch(
                            checked = isDarkMode == true,
                            onCheckedChange = {
                                viewModel.toggleDarkMode()
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = {
                        viewModel.viewModelScope.launch {
                            viewModel.LogOut(navController)
                        }
                    }) {
                        Text("Log Out")
                    }

                    Divider(
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )

                    Text("OnlineShop", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}


