package com.example.OnlineShop.domain.use_cases.Product

import com.example.OnlineShop.domain.repository.ProductRepository
import com.example.OnlineShop.presenter.dto.Product.CategoriesDTO
import com.example.retrofit.presenter.model.DtoResponse
import retrofit2.Response
import javax.inject.Inject

class GetCategoriesUseCase  @Inject constructor(
    private val repository: ProductRepository
) {
    suspend fun execute(): Response<DtoResponse<List<CategoriesDTO>>> {
        return repository.getCategories()
    }
}