package com.example.OnlineShop.domain.use_cases.Product

data class ProductUseCases(
  val getCategoriesUseCase:GetCategoriesUseCase,
  val getProductsUseCase:GetProductsUseCase,
  val getProductUseCase: GetProductUseCase,
  val getProductReviewsUseCase:GetProductReviewsUseCase,
  val addProductReviewUseCase:AddProductReviewUseCase
)
