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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
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
import com.example.OnlineShop.presenter.ui.transaction.TransactionItemList
import com.example.OnlineShop.presenter.view_model.CheckoutViewModel
import com.example.OnlineShop.ui.theme.DarkColorScheme
import com.example.OnlineShop.ui.theme.LightColorScheme
import com.example.retrofit.presenter.view_model.AuthViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTransactionScreen(navController: NavController, orderId: Int) {
    val viewModel: CheckoutViewModel = hiltViewModel()
    LaunchedEffect(orderId) {
        viewModel.getUserOrderedProducts(orderId)
    }

  val orderedProducts by viewModel.userOrderedProducts.collectAsState()
    val authModel: AuthViewModel = hiltViewModel()
    val isDarkMode by rememberUpdatedState(newValue = authModel.isDarkMode.value)

    val colorScheme = if (isDarkMode == true) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Order Details") },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.navigate("profileScreen") }
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
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(it)
                ) {
                    TransactionItemList(cartProductData = orderedProducts?.products.orEmpty())
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
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(start = 16.dp)
                        ) {
                            Text(
                                text = "Order ID",
                                fontSize = 12.sp,
                                color = Color.Black
                            )
                            Text(
                                text = "#$orderId",
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }

                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = "Total",
                                fontSize = 12.sp,
                                color = Color.Black
                            )
                            Text(
                                text = "${orderedProducts?.totalPrice} $",
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }

                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(end = 16.dp)
                        ) {
                            Text(
                                text = orderedProducts?.orderDate ?: "N/A",
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                    }


                }

            }
        )
    }
}











