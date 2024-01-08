package com.example.OnlineShop.domain.use_cases.Auth

import com.example.retrofit.domain.repository.AuthRepository
import com.example.retrofit.presenter.model.DtoResponse
import com.example.OnlineShop.presenter.dto.Auth.UserDTO
import com.example.OnlineShop.presenter.dto.Auth.loginDTO
import retrofit2.Response
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend fun execute(
        user: loginDTO
    ): Response<DtoResponse<UserDTO>> {
        return repository.Login(user)
    }
}