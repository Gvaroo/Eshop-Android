package com.example.OnlineShop.domain.use_cases.Product

import com.example.OnlineShop.domain.repository.ProductRepository
import com.example.OnlineShop.presenter.dto.Product.AddProductReviewDTO
import com.example.retrofit.presenter.model.DtoResponse
import retrofit2.Response
import javax.inject.Inject

class AddProductReviewUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend fun execute(review: AddProductReviewDTO): Response<DtoResponse<String>> {
        return repository.addProductReview(review)
    }
}