package com.example.OnlineShop.presenter.dto.Checkout

import com.example.OnlineShop.presenter.dto.Cart.CartProductDTO
import java.math.BigDecimal

data class GetOrderDTO(
    val orderId:Int,
    val orderDate: String? = null,
    val totalPrice:BigDecimal? = null,
    val products:List<CartProductDTO> = emptyList()
)
