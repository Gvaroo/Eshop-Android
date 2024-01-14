package com.example.OnlineShop.presenter.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.OnlineShop.R

import com.example.OnlineShop.presenter.dto.Auth.loginDTO
import com.example.OnlineShop.ui.theme.DarkColorScheme
import com.example.OnlineShop.ui.theme.LightColorScheme
import com.example.retrofit.presenter.view_model.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {
    val viewModel: AuthViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val errorMessage by viewModel.errorMessage
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isDarkMode by rememberUpdatedState(newValue = viewModel.isDarkMode.value)
    val colorScheme = if (isDarkMode == true) DarkColorScheme else LightColorScheme

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.padding(16.dp),
        snackbar = {
            Snackbar(
                action = {},
                content = {
                    Text(text = errorMessage ?: "error")
                }
            )
        }
    )
    MaterialTheme(
        colorScheme = colorScheme,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Login",
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = "By signing in, you agree to our ",
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "terms and policy.",
                    fontSize = 14.sp,
                    color = Color.Blue,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Login",
                    fontSize = 18.sp,
                    color = Color.Blue,
                    modifier = Modifier.padding(end = 8.dp)

                )
                Text(
                    text = "Register",
                    fontSize = 18.sp,
                    color = Color.Gray,
                    modifier = Modifier.clickable { navController.navigate("registerScreen") }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp)
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address") },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.mail),
                            contentDescription = null,
                            modifier = Modifier
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.lock),
                            contentDescription = null,
                            modifier = Modifier
                        )
                    },
                    trailingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.showpassword),
                            contentDescription = null,
                            modifier = Modifier
                        )

                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )

                Button(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                viewModel.Login(
                                    loginDTO(email, password), navController
                                )

                            } catch (e: Exception) {
                                e.message?.let { Log.d("!!!!!!", it) }
                            }
                        }


                    },
                    colors = ButtonDefaults.buttonColors(Color.Blue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Login")
                }
            }
        }
        errorMessage?.let { message ->
            scope.launch {
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Short
                )
                viewModel.clearErrorMessage()
            }
        }
    }
    }
