package com.example.OnlineShop.domain.use_cases.Cart

import com.example.OnlineShop.domain.repository.CartRepository
import com.example.retrofit.presenter.model.DtoResponse
import retrofit2.Response
import javax.inject.Inject

class DeleteProductFromCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend fun execute(productId:Int): Response<DtoResponse<String>> {
        return repository.DeleteProductFromCart(productId)
    }
}