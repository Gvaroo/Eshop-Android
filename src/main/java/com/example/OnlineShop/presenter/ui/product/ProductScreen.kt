package com.example.OnlineShop.presenter.ui.product

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.OnlineShop.presenter.view_model.ProductViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun ProductScreen(navController: NavHostController) {
    val productViewModel: ProductViewModel = hiltViewModel()
    val productList by rememberUpdatedState(newValue = runBlocking { productViewModel.getProducts() })
    val categorySelected by productViewModel.categorySelected.collectAsState()
    val searchedText by productViewModel.searchText.collectAsState()

    Text(
        text = "All Products",
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(8.dp)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
//            .padding(16.dp)
    ) {
        items(
            productList?.let { products ->
                if (categorySelected.isNullOrBlank() || categorySelected == "All") {
                    if (!searchedText.isNullOrBlank()) {
                        products.filter { it.name.contains(searchedText!!, ignoreCase = true) }
                    } else {
                        products
                    }
                } else {
                    products.filter { product ->
                        (product.category.name == categorySelected) &&
                                (searchedText.isNullOrBlank() || product.name.contains(searchedText!!, ignoreCase = true))
                    }
                }
            } ?: emptyList()
        ) { product ->
            ProductCard(
                productId = product.id,
                productName = product.name,
                category = product.category.name,
                price = product.price,
                imageUrl = product.image.imageUrl,
                navController
            )

        }
    }
}