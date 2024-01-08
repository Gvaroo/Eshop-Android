package com.example.OnlineShop.domain.use_cases.Auth

import com.example.retrofit.domain.repository.AuthRepository
import com.example.retrofit.presenter.model.DtoResponse
import retrofit2.Response
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend fun execute(
    ): Response<DtoResponse<Boolean>> {
        return repository.IsLoggedIn()
    }
}