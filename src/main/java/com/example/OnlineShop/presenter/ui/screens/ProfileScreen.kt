package com.example.OnlineShop.presenter.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.OnlineShop.presenter.dto.Checkout.GetOrderHistoryDTO
import com.example.OnlineShop.presenter.ui.navbar.BottomBar
import com.example.OnlineShop.presenter.ui.transaction.TransactionItem
import com.example.OnlineShop.presenter.view_model.CheckoutViewModel
import com.example.OnlineShop.ui.theme.DarkColorScheme
import com.example.OnlineShop.ui.theme.LightColorScheme
import com.example.retrofit.presenter.view_model.AuthViewModel
import androidx.compose.foundation.lazy.LazyColumn as LazyColumn

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {
    val viewModel: CheckoutViewModel = hiltViewModel()
    val authViewModel:AuthViewModel = hiltViewModel()
    val transactions by produceState<List<GetOrderHistoryDTO>?>(initialValue = null) {
        value = viewModel.getUserOrderHistory()
    }
    var userInfo = authViewModel.getUserInformation()
    val isDarkMode by rememberUpdatedState(newValue = authViewModel.isDarkMode.value)
    val colorScheme = if (isDarkMode == true) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
    ) {
        Scaffold(
            bottomBar = { BottomBar(navController = navController) },
            topBar = {
                TopAppBar(
                    title = { Text(text = "Profile") },
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
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = it.calculateTopPadding(),
                        bottom = it.calculateBottomPadding()
                    )
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(16.dp),
                    shape = MaterialTheme.shapes.medium,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(64.dp)
                                .background(MaterialTheme.colorScheme.primary, CircleShape)
                                .padding(8.dp)
                                .clip(CircleShape)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = userInfo[0] ,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Text(
                                text = userInfo[1],
                                color = Color.Black
                            )
                        }
                    }
                }

                Text(
                    text = "Transactions",
                    modifier = Modifier.padding(top = 16.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp)
                ) {
                    items(transactions ?: emptyList()) { transaction ->
                        TransactionItem(transaction, navController)
                    }
                }
            }
        }
    }
}



