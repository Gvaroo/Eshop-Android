package com.example.OnlineShop.presenter.ui.transaction

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.OnlineShop.presenter.dto.Cart.CartProductDTO

@Composable
fun TransactionItemList(cartProductData: List<CartProductDTO>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(

        ) {
            items(cartProductData) { cartItem ->
                TransactionItemCard(cartItem)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}