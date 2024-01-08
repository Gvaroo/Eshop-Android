package com.example.OnlineShop.presenter.dto.Auth

data class UserDTO(
    val fullName:String,
    val email:String? = null,
    val password:String? = null
)
