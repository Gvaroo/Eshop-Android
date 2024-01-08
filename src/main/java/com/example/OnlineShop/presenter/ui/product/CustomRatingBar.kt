package com.example.OnlineShop.presenter.ui.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomRatingBar(rating: Int,Arrangement: Arrangement.Horizontal) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement
    ) {
        repeat(rating) { index ->
            val icon = Icons.Default.Star
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier
                    .size(16.dp)
                    .padding(end = 4.dp)
            )
        }
    }
}