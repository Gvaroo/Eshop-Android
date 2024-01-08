package com.example.OnlineShop.domain.use_cases.Product

import com.example.OnlineShop.domain.repository.ProductRepository
import com.example.OnlineShop.presenter.dto.Product.ProductDTO
import com.example.retrofit.presenter.model.DtoResponse
import retrofit2.Response
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend fun execute(id: Int):Response<DtoResponse<ProductDTO>> {
        return repository.getProduct(id)
    }
}