package com.example.retrofit.data.repositoryImpl

import com.example.retrofit.data.remote.OnlineShopApi
import com.example.retrofit.domain.repository.AuthRepository
import com.example.retrofit.presenter.model.DtoResponse
import com.example.OnlineShop.presenter.dto.Auth.UserDTO
import com.example.OnlineShop.presenter.dto.Auth.loginDTO
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: OnlineShopApi
) : AuthRepository {
    override suspend fun Register(user: UserDTO): Response<DtoResponse<Int>> = api.Register(user)
    override suspend fun Login(user: loginDTO): Response<DtoResponse<UserDTO>> = api.Login(user)
    override suspend fun IsLoggedIn(): Response<DtoResponse<Boolean>> = api.IsLoggedIn()
    override suspend fun LogOut(): Response<ResponseBody> = api.LogOut()
}