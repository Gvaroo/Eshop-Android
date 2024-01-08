package com.example.OnlineShop.data.repositoryImpl

import com.example.OnlineShop.domain.repository.CartRepository
import com.example.OnlineShop.presenter.dto.Cart.CartDataDTO
import com.example.OnlineShop.presenter.dto.Cart.CartProductDTO
import com.example.retrofit.data.remote.OnlineShopApi
import com.example.retrofit.presenter.model.DtoResponse
import retrofit2.Response
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val api: OnlineShopApi
) :CartRepository {
    override suspend fun AddOrUpdateProductsToCart(cartData: MutableList<CartDataDTO>): Response<DtoResponse<String>> = api.AddOrUpdateProductsToCart(cartData)
    override suspend fun GetUserCartData(): Response<DtoResponse<List<CartProductDTO>>> = api.GetUserCartData()
    override suspend fun DeleteProductFromCart(productId: Int): Response<DtoResponse<String>> = api.DeleteProductFromCart(productId)
}