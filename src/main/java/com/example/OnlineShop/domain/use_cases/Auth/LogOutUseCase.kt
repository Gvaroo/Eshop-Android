package com.example.OnlineShop.domain.use_cases.Auth

import com.example.retrofit.domain.repository.AuthRepository
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend fun execute(

    ): Response<ResponseBody> {
        return repository.LogOut()
    }
}