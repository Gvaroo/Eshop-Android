package com.example.OnlineShop.domain.repository

import com.example.OnlineShop.presenter.dto.Product.AddProductReviewDTO
import com.example.OnlineShop.presenter.dto.Product.CategoriesDTO
import com.example.OnlineShop.presenter.dto.Product.GetProductRatingAndReviewDTO
import com.example.OnlineShop.presenter.dto.Product.ProductDTO
import com.example.retrofit.presenter.model.DtoResponse
import retrofit2.Response

interface ProductRepository {
    suspend fun getCategories(): Response<DtoResponse<List<CategoriesDTO>>>
    suspend fun getProducts():Response<DtoResponse<List<ProductDTO>>>
    suspend fun getProduct(id: Int):Response<DtoResponse<ProductDTO>>
    suspend fun getProductReviews(id: Int):Response<DtoResponse<GetProductRatingAndReviewDTO>>
    suspend fun addProductReview(review: AddProductReviewDTO):Response<DtoResponse<String>>

}