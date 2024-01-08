package com.example.OnlineShop.domain.repository

import com.example.OnlineShop.presenter.dto.Checkout.CheckoutDTO
import com.example.OnlineShop.presenter.dto.Checkout.GetOrderDTO
import com.example.OnlineShop.presenter.dto.Checkout.GetOrderHistoryDTO
import com.example.OnlineShop.presenter.dto.Checkout.ShippingInfoDTO
import com.example.retrofit.presenter.model.DtoResponse
import retrofit2.Response

interface CheckoutRepository {
    suspend fun getShippingInfo(): Response<DtoResponse<ShippingInfoDTO>>
    suspend fun checkout(checkoutData: CheckoutDTO):Response<DtoResponse<GetOrderDTO>>
    suspend fun GetUserOrderHistory():Response<DtoResponse<List<GetOrderHistoryDTO>>>
    suspend fun getUserOrderedProducts(orderId:Int):Response<DtoResponse<GetOrderDTO>>
}