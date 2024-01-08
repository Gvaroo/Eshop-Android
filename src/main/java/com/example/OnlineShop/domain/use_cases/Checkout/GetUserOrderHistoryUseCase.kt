package com.example.OnlineShop.domain.use_cases.Checkout

import com.example.OnlineShop.domain.repository.CheckoutRepository
import com.example.OnlineShop.presenter.dto.Checkout.GetOrderHistoryDTO
import com.example.retrofit.presenter.model.DtoResponse
import retrofit2.Response
import javax.inject.Inject

class GetUserOrderHistoryUseCase @Inject constructor(
    private val repository: CheckoutRepository
) {
    suspend fun execute(): Response<DtoResponse<List<GetOrderHistoryDTO>>> {
        return repository.GetUserOrderHistory()
    }
}