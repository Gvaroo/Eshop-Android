package com.example.OnlineShop.domain.use_cases.Cart

data class CartUseCases(
    val addOrUpdateProductsToCartUseCase:AddOrUpdateProductsToCartUseCase,
    val getUserCartDataUseCase:GetUserCartDataUseCase,
    val deleteProductFromCartUseCase:DeleteProductFromCartUseCase
)
