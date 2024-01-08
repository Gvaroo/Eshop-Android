package com.example.OnlineShop.presenter.ui.transaction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.OnlineShop.presenter.dto.Checkout.GetOrderHistoryDTO

@Composable
fun TransactionItem(transaction: GetOrderHistoryDTO, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Order ID: ${transaction.orderId}",
                )
                Text(
                    text = "Detail",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        if (transaction.orderId != null) {
                            navController.navigate("detailTransactionScreen/${transaction.orderId}")
                        }
                    }
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Detail",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                        .clickable {
                            if (transaction.orderId != null) {
                                navController.navigate("detailTransactionScreen/${transaction.orderId}")
                            }
                        }
                )
            }

            Text(
                text = "${transaction.orderDate}",

                )
        }
    }
}