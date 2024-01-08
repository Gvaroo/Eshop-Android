package com.example.OnlineShop.domain.use_cases.Auth

import com.example.retrofit.domain.repository.AuthRepository
import com.example.retrofit.presenter.model.DtoResponse
import com.example.OnlineShop.presenter.dto.Auth.UserDTO
import retrofit2.Response
import javax.inject.Inject

class RegisterByUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend fun execute(
        user: UserDTO
    ): Response<DtoResponse<Int>> {
        return repository.Register(user)
    }
}