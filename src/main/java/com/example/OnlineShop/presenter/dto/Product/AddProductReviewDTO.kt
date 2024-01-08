package com.example.OnlineShop.presenter.dto.Product

data class AddProductReviewDTO(
    val productId:Int,
    val ratingValue:Int,
    val review:String
)
