package com.example.OnlineShop.presenter.dto.Product

data class ProductDTO(
    val id:Int,
    val name:String,
    val price:Int,
    val description:String,
    val quantity:Int,
    val category: CategoriesDTO,
    val image: ProductImageDTO,
    val averageRating:Int,
    val reviewsCount:Int,

    )
