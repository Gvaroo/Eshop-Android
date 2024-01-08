package com.example.OnlineShop.domain.repository

import com.example.OnlineShop.presenter.dto.Cart.CartDataDTO
import com.example.OnlineShop.presenter.dto.Cart.CartProductDTO
import com.example.retrofit.presenter.model.DtoResponse
import retrofit2.Response

interface CartRepository {
    suspend fun AddOrUpdateProductsToCart(cartData:MutableList<CartDataDTO>): Response<DtoResponse<String>>
    suspend fun GetUserCartData() : Response<DtoResponse<List<CartProductDTO>>>
    suspend fun DeleteProductFromCart(productId:Int):Response<DtoResponse<String>>
}