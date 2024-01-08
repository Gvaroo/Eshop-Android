package com.example.OnlineShop.domain.use_cases.Cart

import com.example.OnlineShop.domain.repository.CartRepository
import com.example.OnlineShop.presenter.dto.Cart.CartProductDTO
import com.example.retrofit.presenter.model.DtoResponse
import retrofit2.Response
import javax.inject.Inject

class GetUserCartDataUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend fun execute(): Response<DtoResponse<List<CartProductDTO>>> {
        return repository.GetUserCartData()
    }
}