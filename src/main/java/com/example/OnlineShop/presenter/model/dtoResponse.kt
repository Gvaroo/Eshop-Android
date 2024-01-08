package com.example.retrofit.presenter.model

data class DtoResponse<T>(
    val data: T? = null,
    val message: String? = null,
    val success: Boolean
)