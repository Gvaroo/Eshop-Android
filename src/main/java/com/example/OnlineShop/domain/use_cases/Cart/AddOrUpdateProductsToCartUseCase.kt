package com.example.OnlineShop.domain.use_cases.Cart

import com.example.OnlineShop.domain.repository.CartRepository
import com.example.OnlineShop.presenter.dto.Cart.CartDataDTO
import com.example.retrofit.presenter.model.DtoResponse
import retrofit2.Response
import javax.inject.Inject

class AddOrUpdateProductsToCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend fun execute(cartData: MutableList<CartDataDTO>): Response<DtoResponse<String>> {
        return repository.AddOrUpdateProductsToCart(cartData)
    }
}