package com.example.OnlineShop.presenter.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.OnlineShop.R
import com.example.OnlineShop.presenter.dto.Product.AddProductReviewDTO
import com.example.OnlineShop.presenter.dto.Product.ProductDTO
import com.example.OnlineShop.presenter.dto.Product.ProductReviewsDTO
import com.example.OnlineShop.presenter.view_model.ProductViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun DetailProductContent(
    modifier: Modifier = Modifier,
    product: ProductDTO,
    productReviews: List<ProductReviewsDTO>?
) {
    val productViewModel: ProductViewModel = hiltViewModel()
    val errorMessage by productViewModel.errorMessage
    var isAddReviewDialogVisible by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {

                GlideImage(
                    model = product?.image?.imageUrl,
                    contentDescription = "testing",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(MaterialTheme.colorScheme.background)
                )
                Spacer(modifier = Modifier.height(5.dp))
            }

            item {

                product?.let {
                    Text(
                        text = it.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    product?.let {
                        Text(
                            text = "${it.price} USD",
                            fontWeight = FontWeight.Bold,
                            fontSize = 21.sp,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))


                    product?.let { CustomRatingBar(it.averageRating, Arrangement.End) }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            item {

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(Color.Black)
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            item {

                product?.let {
                    Text(
                        text = product.description,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(Color.Black)
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "User Reviews",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    )

                    Text(
                        text = "Add Review",
                        fontWeight = FontWeight.Bold,
                        color = Color.Red,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .clickable {
                                isAddReviewDialogVisible = true
                            }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            productReviews?.forEach { review ->
                item {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                GlideImage(
                                    model = R.drawable.person,
                                    contentDescription = "User Avatar",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = review.user.fullName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))


                            CustomRatingBar(review.productRating.ratingValue, Arrangement.Start)

                            Spacer(modifier = Modifier.height(8.dp))


                            Text(
                                text = review.reviewText,
                                modifier = Modifier.fillMaxWidth(),
                                color = Color.Black
                            )
                        }
                    }
                }
            }

            item {

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(Color.Black)
                )

                Spacer(modifier = Modifier.height(8.dp))
            }


        }
    }
    if (isAddReviewDialogVisible) {
        AddReviewDialog(
            onReviewSubmitted = { rating, reviewText ->
                productViewModel.viewModelScope.launch {
                    productViewModel.addProductReviews(
                        AddProductReviewDTO(
                            productId = product.id,
                            ratingValue = rating,
                            review = reviewText
                        )
                    )
                    isAddReviewDialogVisible = false
                }
            },
            onDismiss = {
                isAddReviewDialogVisible = false
            }
        )
    }
    errorMessage?.let { message ->
        Snackbar(
            modifier = Modifier.padding(16.dp),
            action = {
            }
        ) {
            Text(text = message)
        }
    }
}