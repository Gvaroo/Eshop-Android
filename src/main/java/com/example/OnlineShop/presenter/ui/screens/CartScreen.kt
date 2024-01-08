package com.example.OnlineShop.presenter.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.OnlineShop.presenter.dto.Cart.CartProductDTO
import com.example.OnlineShop.presenter.ui.cart.CartItemCard
import com.example.OnlineShop.presenter.view_model.CartViewModel
import com.example.OnlineShop.ui.theme.DarkColorScheme
import com.example.OnlineShop.ui.theme.LightColorScheme
import com.example.retrofit.presenter.view_model.AuthViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController) {
    val ViewModel: CartViewModel = hiltViewModel()
    LaunchedEffect(ViewModel) {
        ViewModel.GetUserCartData()

    }
    val cartData by ViewModel.cartData.collectAsState()
    val viewModel: AuthViewModel = hiltViewModel()
    val isDarkMode by rememberUpdatedState(newValue = viewModel.isDarkMode.value)

    val colorScheme = if (isDarkMode == true) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Carts") },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.navigate("mainScreen") }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                )
            },
            content = {
                if (cartData.isNullOrEmpty()) {


                } else {
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(it)
                    ) {
                        CartItemList(cartProductData = cartData!!)
                    }
                }
            },
            bottomBar = {
                BottomAppBar(
                    contentPadding = PaddingValues(10.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Total",
                                fontSize = 12.sp,
                                color = Color.Black

                            )

                            Text(
                                text = "${cartData?.let { calculateTotal(it) }} $" ?: "N/A",
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }

                        Button(
                            onClick = {
                                val total: Int? =
                                    cartData?.let { calculateTotal(it) }?.toIntOrNull()
                                navController.navigate("checkoutScreen/$total")
                            },
                            modifier = Modifier
                                .height(48.dp)
                                .padding(horizontal = 16.dp),
                            colors = ButtonDefaults.buttonColors(
                            )
                        ) {
                            Text(
                                text = "Process to Checkout",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun CartItemList(cartProductData: List<CartProductDTO>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(

        ) {
            items(cartProductData) { cartItem ->
                CartItemCard(cartItem)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


fun calculateTotal(cartProductData: List<CartProductDTO>): String {
    val totalPrice = cartProductData.sumBy { it.quantity * it.price }
    return "$totalPrice"
}







