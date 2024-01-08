package com.example.OnlineShop.presenter.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.OnlineShop.presenter.view_model.ProductViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductCard(
    productId:Int,
    productName: String,
    category: String,
    price: Int,
    imageUrl: String,
    navController: NavHostController

) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp)
            .clickable {
                navController.navigate("detailProductScreen/$productId")

            }
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            GlideImage(
                model = imageUrl,
                contentDescription = "testing",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Text(
                text = productName,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = category,
                    fontSize = 14.sp
                )
                Text(
                    text = "$$price",
                    fontSize = 14.sp
                )

            }
        }

    }
}