package com.example.OnlineShop.presenter.dto.Checkout

data class GetOrderHistoryDTO(
    val orderId:Int,
    val name:String? = null,
    val quantity:Int? = null,
    val orderDate: String? = null
)
