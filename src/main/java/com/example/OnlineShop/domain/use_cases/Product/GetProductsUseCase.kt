package com.example.OnlineShop.domain.use_cases.Product

import com.example.OnlineShop.domain.repository.ProductRepository
import com.example.OnlineShop.presenter.dto.Product.ProductDTO
import com.example.retrofit.presenter.model.DtoResponse
import retrofit2.Response
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend fun execute(): Response<DtoResponse<List<ProductDTO>>> {
        return repository.getProducts()
    }
}