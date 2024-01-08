package com.example.retrofit.data.remote

import com.example.OnlineShop.presenter.dto.Cart.CartDataDTO
import com.example.OnlineShop.presenter.dto.Cart.CartProductDTO
import com.example.OnlineShop.presenter.dto.Checkout.CheckoutDTO
import com.example.OnlineShop.presenter.dto.Checkout.GetOrderDTO
import com.example.OnlineShop.presenter.dto.Checkout.GetOrderHistoryDTO
import com.example.OnlineShop.presenter.dto.Checkout.ShippingInfoDTO
import com.example.OnlineShop.presenter.dto.Product.AddProductReviewDTO
import com.example.OnlineShop.presenter.dto.Product.CategoriesDTO
import com.example.OnlineShop.presenter.dto.Product.GetProductRatingAndReviewDTO
import com.example.OnlineShop.presenter.dto.Product.ProductDTO
import com.example.retrofit.presenter.model.DtoResponse
import com.example.OnlineShop.presenter.dto.Auth.UserDTO
import com.example.OnlineShop.presenter.dto.Auth.loginDTO
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OnlineShopApi {


    @POST("api/Auth/Register")
    suspend fun Register(@Body user: UserDTO):Response<DtoResponse<Int>>
    @POST("api/Auth/Login")
    suspend fun Login(@Body user: loginDTO):Response<DtoResponse<UserDTO>>

    @GET("api/Auth/IsLoggedIn")
    suspend fun IsLoggedIn():Response<DtoResponse<Boolean>>

    @GET("api/Auth/LogOut")
    suspend fun LogOut():Response<ResponseBody>

    //Products

    @GET("api/Product/GetCategoriesAndSubCategories")
    suspend fun getCategories():Response<DtoResponse<List<CategoriesDTO>>>

    @GET("api/Product/GetAllProducts")
    suspend fun getProducts():Response<DtoResponse<List<ProductDTO>>>
    @GET("api/Product/GetProduct/{id}")
    suspend fun getProduct(@Path("id") id: Int):Response<DtoResponse<ProductDTO>>
    @GET("api/Product/GetProductRatingsAndReviews/{id}")
    suspend fun getProductReviews(@Path("id") id: Int):Response<DtoResponse<GetProductRatingAndReviewDTO>>
    @POST("/api/Product/AddProductReviewAndRating")
    suspend fun addProductReview(@Body review: AddProductReviewDTO):Response<DtoResponse<String>>


    //Cart
    @POST("/api/Cart/AddOrUpdateProductsToCart")
    suspend fun AddOrUpdateProductsToCart(@Body cartData:MutableList<CartDataDTO>): Response<DtoResponse<String>>
    @POST("/api/Cart/DeleteProductFromCart/{productId}")
    suspend fun DeleteProductFromCart(@Path("productId") productId:Int):Response<DtoResponse<String>>

    //Checkout
    @GET("api/Cart/GetShippingInfo")
    suspend fun getShippingInfo():Response<DtoResponse<ShippingInfoDTO>>
    @POST("api/Cart/Checkout")
    suspend fun checkout(@Body checkoutData: CheckoutDTO):Response<DtoResponse<GetOrderDTO>>
    @GET("api/Cart/GetUserCartData")
    suspend fun GetUserCartData() : Response<DtoResponse<List<CartProductDTO>>>
    @GET("api/Cart/GetUserOrderHistory")
    suspend fun GetUserOrderHistory():Response<DtoResponse<List<GetOrderHistoryDTO>>>
    @GET("/api/Cart/GetOrderedProducts/{orderId}")
    suspend fun getUserOrderHistory(@Path("orderId") orderId:Int):Response<DtoResponse<GetOrderDTO>>
}