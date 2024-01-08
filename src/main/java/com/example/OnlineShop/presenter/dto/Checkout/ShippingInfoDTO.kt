package com.example.OnlineShop.presenter.dto.Checkout

data class ShippingInfoDTO(
    val shippingAddress:String? = null,
    val shippingCountry:String? = null,
    val shippingCity:String? = null,
    val zipCode:String? = null,
    val phoneNumber:Int? = null
)
