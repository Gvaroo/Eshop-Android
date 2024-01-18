package com.example.OnlineShop.presenter.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.isLiveLiteralsEnabled
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.OnlineShop.R

import com.example.OnlineShop.presenter.dto.Cart.CartDataDTO
import com.example.OnlineShop.presenter.dto.Product.ProductDTO
import com.example.OnlineShop.presenter.ui.product.DetailProductContent
import com.example.OnlineShop.presenter.view_model.CartViewModel
import com.example.OnlineShop.presenter.view_model.ProductViewModel
import com.example.OnlineShop.ui.theme.DarkColorScheme
import com.example.OnlineShop.ui.theme.LightColorScheme
import com.example.retrofit.presenter.view_model.AuthViewModel
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Row as Row

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun DetailProductScreen(navController: NavController, productId: Int) {
    val productViewModel: ProductViewModel = hiltViewModel()
    val cartViewModel:CartViewModel = hiltViewModel()
    val product by produceState<ProductDTO?>(initialValue = null) {
        value = productViewModel.getProductById(productId)
    }
    LaunchedEffect(productId) {
        productViewModel.getProductReviews(productId)
    }
    val productReviews by productViewModel.productReviewsState.collectAsState()
    val errorMessage by productViewModel.errorMessage
    val message by cartViewModel.Message
    val cartProductData: MutableList<CartDataDTO> = remember { mutableListOf() }
    var addToCartClicked by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val viewModel: AuthViewModel = hiltViewModel()
    val isDarkMode by rememberUpdatedState(newValue = viewModel.isDarkMode.value)

    val colorScheme = if (isDarkMode == true) DarkColorScheme else LightColorScheme

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
        ) {
            var quantity by remember { mutableStateOf(1) }

            Scaffold(
                bottomBar = {
                    BottomAppBar {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            FloatingActionButton(
                                onClick = {
                                    if ((product?.quantity ?: 0) > 0) {
                                        if (quantity > 1) {
                                            quantity--
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.remove),
                                    contentDescription = "Decrease",
                                    tint = LocalContentColor.current
                                )
                            }

                            Text(
                                text = if (product?.quantity == 0) "Out of Stock" else "$quantity",
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                                color = if (product?.quantity == 0) Color.Gray else Color.Black,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(
                                        start = 8.dp,
                                        end = 8.dp,
                                        top = 4.dp
                                    )
                            )

                            FloatingActionButton(
                                onClick = {
                                    if ((product?.quantity ?: 0) > 0) {
                                        if (quantity < product?.quantity ?: 1) {
                                            quantity++
                                        }
                                    }

                                },

                                ) {
                                Icon(
                                    imageVector = Default.Add,
                                    contentDescription = "Increase Quantity"
                                )
                            }

                            Button(
                                onClick = {
                                    if ((product?.quantity ?: 0) > 0) {
                                        addToCartClicked = true
                                    } else {
                                        productViewModel.showSnackbar("This product is out of stock")
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                                    .padding(horizontal = 16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = MaterialTheme.colorScheme.onSecondary
                                ),
                                shape = RoundedCornerShape(24.dp)
                            ) {
                                Text(text = "Add to Cart", fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                }

            )
            { innerPadding ->

                product?.let {
                    DetailProductContent(
                        Modifier.padding(innerPadding),
                        it,
                        productReviews
                    )
                }
            }
            LaunchedEffect(addToCartClicked) {
                if (addToCartClicked) {
                    product?.let {
                        cartProductData.add(CartDataDTO(it.id, quantity))
                        cartViewModel.AddOrUpdateProductsToCart(cartProductData)
                        addToCartClicked = false
                    }
                }
            }
            message?.let { message ->
                scope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        message = message,
                        duration = SnackbarDuration.Short
                    )
                    cartViewModel.clearErrorMessage()
                }
            }
            errorMessage?.let { message ->
                scope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        message = message,
                        duration = SnackbarDuration.Short
                    )
                    productViewModel.clearErrorMessage()
                }
            }

        }
    }
}




















