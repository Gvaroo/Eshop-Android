package com.example.OnlineShop.data.repositoryImpl

import com.example.OnlineShop.domain.repository.ProductRepository
import com.example.OnlineShop.presenter.dto.Product.AddProductReviewDTO
import com.example.OnlineShop.presenter.dto.Product.CategoriesDTO
import com.example.OnlineShop.presenter.dto.Product.GetProductRatingAndReviewDTO
import com.example.OnlineShop.presenter.dto.Product.ProductDTO
import com.example.retrofit.data.remote.OnlineShopApi
import com.example.retrofit.presenter.model.DtoResponse
import retrofit2.Response
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: OnlineShopApi
) :ProductRepository {
    override suspend fun getCategories(): Response<DtoResponse<List<CategoriesDTO>>> = api.getCategories()
    override suspend fun getProducts(): Response<DtoResponse<List<ProductDTO>>> =api.getProducts()
    override suspend fun getProduct(id: Int): Response<DtoResponse<ProductDTO>> = api.getProduct(id)
    override suspend fun getProductReviews(id: Int): Response<DtoResponse<GetProductRatingAndReviewDTO>> =api.getProductReviews(id)
    override suspend fun addProductReview(review: AddProductReviewDTO): Response<DtoResponse<String>> =api.addProductReview(review)



}