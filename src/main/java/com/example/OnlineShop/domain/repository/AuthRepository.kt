package com.example.retrofit.domain.repository

import com.example.retrofit.presenter.model.DtoResponse
import com.example.OnlineShop.presenter.dto.Auth.UserDTO
import com.example.OnlineShop.presenter.dto.Auth.loginDTO
import okhttp3.ResponseBody
import retrofit2.Response

interface AuthRepository {
    suspend fun Register(user: UserDTO):Response<DtoResponse<Int>>
    suspend fun Login(user: loginDTO):Response<DtoResponse<UserDTO>>
    suspend fun IsLoggedIn():Response<DtoResponse<Boolean>>
    suspend fun LogOut():Response<ResponseBody>
}