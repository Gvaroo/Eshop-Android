package com.example.OnlineShop.presenter.ui.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.OnlineShop.presenter.view_model.ProductViewModel

@Composable
fun CategoryCard(text: String) {
    val productViewModel: ProductViewModel = hiltViewModel()
    Card(
        modifier = Modifier
            .background(Color.White)
            .padding(8.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(8.dp)
                .clickable { productViewModel.selectCategory(text) }
                .wrapContentHeight()
        )
    }
}