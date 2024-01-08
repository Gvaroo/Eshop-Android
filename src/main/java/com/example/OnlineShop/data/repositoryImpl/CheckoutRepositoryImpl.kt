package com.example.OnlineShop.data.repositoryImpl

import com.example.OnlineShop.domain.repository.CheckoutRepository
import com.example.OnlineShop.presenter.dto.Checkout.CheckoutDTO
import com.example.OnlineShop.presenter.dto.Checkout.GetOrderDTO
import com.example.OnlineShop.presenter.dto.Checkout.GetOrderHistoryDTO
import com.example.OnlineShop.presenter.dto.Checkout.ShippingInfoDTO
import com.example.retrofit.data.remote.OnlineShopApi
import com.example.retrofit.presenter.model.DtoResponse
import retrofit2.Response
import javax.inject.Inject

class CheckoutRepositoryImpl@Inject constructor(
    private val api: OnlineShopApi
) : CheckoutRepository{
    override suspend fun getShippingInfo(): Response<DtoResponse<ShippingInfoDTO>> = api.getShippingInfo()
    override suspend fun checkout(checkoutData: CheckoutDTO): Response<DtoResponse<GetOrderDTO>> = api.checkout(checkoutData)
    override suspend fun GetUserOrderHistory(): Response<DtoResponse<List<GetOrderHistoryDTO>>> = api.GetUserOrderHistory()
    override suspend fun getUserOrderedProducts(orderId: Int): Response<DtoResponse<GetOrderDTO>> = api.getUserOrderHistory(orderId)


}