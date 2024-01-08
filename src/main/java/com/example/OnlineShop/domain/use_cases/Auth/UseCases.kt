package com.example.OnlineShop.domain.use_cases.Auth

data class UseCases(
    val registerByUseCase : RegisterByUseCase,
    val LoginUseCase : LoginUseCase,
    val IsLoggedInUseCase: IsLoggedInUseCase,
    val LogOutUseCase: LogOutUseCase
)
