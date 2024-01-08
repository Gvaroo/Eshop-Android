package com.example.OnlineShop.presenter.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.OnlineShop.presenter.dto.Cart.CartProductDTO
import com.example.OnlineShop.presenter.view_model.CartViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CartItemCard(cartItem: CartProductDTO) {
    var localQuantity by remember { mutableStateOf(cartItem.quantity) }
    val viewModel: CartViewModel = hiltViewModel()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                GlideImage(
                    model = cartItem?.image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(MaterialTheme.shapes.medium)
                )

                Spacer(modifier = Modifier.width(16.dp))


                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {
                    // Product Title
                    Text(
                        text = cartItem.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))


                    Text(
                        text = "Price: ${cartItem.price} $",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    Spacer(modifier = Modifier.height(4.dp))


                    QuantityPicker(
                        quantity = localQuantity,
                        onDecrease = {
                            if (localQuantity > 1) {
                                localQuantity -= 1
                                viewModel.updateCartItem(cartItem.productId, localQuantity)
                            }
                        },
                        onIncrease = {
                            if(localQuantity <= cartItem.quantity) {
                                localQuantity += 1
                                viewModel.updateCartItem(cartItem.productId, localQuantity)
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))


                Image(
                    Icons.Default.Clear,
                    contentDescription = "Remove",
                    modifier = Modifier
                        .padding(4.dp)
                        .size(24.dp)
                        .clickable {
                            viewModel.viewModelScope.launch {
                                viewModel.DeleteProductFromCart(cartItem.productId)
                            }
                        }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = "Total price: ${cartItem.quantity * cartItem.price} $",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}