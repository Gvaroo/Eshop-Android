package com.example.OnlineShop.presenter.dto.Cart

import java.math.BigDecimal

data class CartProductDTO(
    val productId:Int,
    val name:String,
    val price:Int,
    val quantity:Int,
    val image: String,
    val maxPrice:BigDecimal? = null
)
