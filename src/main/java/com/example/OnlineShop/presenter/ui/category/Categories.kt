package com.example.OnlineShop.presenter.ui.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.OnlineShop.presenter.view_model.ProductViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun Categories() {
    val productViewModel: ProductViewModel = hiltViewModel()

    val categoriesList by rememberUpdatedState(newValue = runBlocking { productViewModel.getCategories() })

    Text(
        text = "Categories",
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(8.dp)
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        item {
            CategoryCard(text = "All")
        }
        categoriesList?.forEach { category ->
            item {
                CategoryCard(text = category.name)
            }
        }
    }
}