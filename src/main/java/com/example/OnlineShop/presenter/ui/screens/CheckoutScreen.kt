package com.example.OnlineShop.presenter.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.OnlineShop.presenter.ui.checkout.PaymentInfoFields
import com.example.OnlineShop.presenter.ui.checkout.ShippingInfoFields
import com.example.OnlineShop.presenter.view_model.CheckoutViewModel
import com.example.OnlineShop.ui.theme.DarkColorScheme
import com.example.OnlineShop.ui.theme.LightColorScheme
import com.example.retrofit.presenter.view_model.AuthViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(navController: NavHostController, totalPrice: Int) {
    val ViewModel: CheckoutViewModel = hiltViewModel()
    LaunchedEffect(totalPrice) {
        ViewModel.getShippingInfo()
    }
    val shippinginfo by ViewModel.shippingInfo.collectAsState()
    val message by ViewModel.Message.collectAsState()

    var cardNumber by remember { mutableStateOf("") }
    var expireDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    val viewModel: AuthViewModel = hiltViewModel()
    val isDarkMode by rememberUpdatedState(newValue = viewModel.isDarkMode.value)

    val colorScheme = if (isDarkMode == true) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Checkout") },
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
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = it.calculateTopPadding(),
                            bottom = 16.dp
                        )
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        item {
                            Text(
                                text = "Shipping Information",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.DarkGray
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ShippingInfoFields(
                                shippingInfo = shippinginfo,
                                onShippingInfoChange = {
                                    ViewModel.updateShippingInfo(it)
                                }
                            )
                        }


                        item {
                            Text(
                                text = "Payment Information",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.DarkGray
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            PaymentInfoFields(
                                cardNumber = cardNumber,
                                onCardNumberChange = { cardNumber = it },
                                expireDate = expireDate,
                                onExpireDateChange = { expireDate = it },
                                cvv = cvv,
                                onCvvChange = { cvv = it }
                            )
                        }


                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Total Price: $totalPrice $",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color(0xFF2196F3)
                            )
                        }


                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    ViewModel.viewModelScope.launch {
                                        ViewModel.checkout(navController)
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .padding(horizontal = 20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = "Checkout")
                            }
                        }
                    }
                }
            }

        )
        message?.let { message ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                }
            ) {
                Text(text = message)
            }
        }
    }
}






