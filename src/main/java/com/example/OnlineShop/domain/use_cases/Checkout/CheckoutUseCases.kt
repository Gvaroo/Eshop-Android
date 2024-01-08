package com.example.OnlineShop.domain.use_cases.Checkout

data class CheckoutUseCases(
    val getShippingInfoUseCase: GetShippingInfoUseCase,
    val orderUseCase: OrderUseCase,
    val getUserOrderHistoryUseCase :GetUserOrderHistoryUseCase,
    val getUserOrderedProducts: GetUserOrderedProducts
)
