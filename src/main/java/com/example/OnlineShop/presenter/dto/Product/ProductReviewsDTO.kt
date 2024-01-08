package com.example.OnlineShop.presenter.dto.Product

import com.example.OnlineShop.presenter.dto.Auth.UserDTO

data class ProductReviewsDTO(
    val reviewText: String,
    val user: UserDTO,
    val productRating: ProductRatingDTO
)
