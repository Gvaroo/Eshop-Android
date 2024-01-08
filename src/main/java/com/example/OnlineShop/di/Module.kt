package com.example.retrofit.di

import android.content.Context
import android.content.SharedPreferences
import com.example.OnlineShop.data.repositoryImpl.CartRepositoryImpl
import com.example.OnlineShop.data.repositoryImpl.CheckoutRepositoryImpl
import com.example.OnlineShop.data.repositoryImpl.ProductRepositoryImpl
import com.example.OnlineShop.domain.repository.CartRepository
import com.example.OnlineShop.domain.repository.CheckoutRepository
import com.example.OnlineShop.domain.repository.ProductRepository
import com.example.OnlineShop.domain.use_cases.Cart.AddOrUpdateProductsToCartUseCase
import com.example.OnlineShop.domain.use_cases.Cart.CartUseCases
import com.example.OnlineShop.domain.use_cases.Cart.DeleteProductFromCartUseCase
import com.example.OnlineShop.domain.use_cases.Cart.GetUserCartDataUseCase
import com.example.OnlineShop.domain.use_cases.Checkout.CheckoutUseCases
import com.example.OnlineShop.domain.use_cases.Checkout.GetShippingInfoUseCase
import com.example.OnlineShop.domain.use_cases.Checkout.GetUserOrderHistoryUseCase
import com.example.OnlineShop.domain.use_cases.Checkout.GetUserOrderedProducts
import com.example.OnlineShop.domain.use_cases.Checkout.OrderUseCase
import com.example.OnlineShop.domain.use_cases.Product.AddProductReviewUseCase
import com.example.OnlineShop.domain.use_cases.Product.GetCategoriesUseCase
import com.example.OnlineShop.domain.use_cases.Product.GetProductReviewsUseCase
import com.example.OnlineShop.domain.use_cases.Product.GetProductUseCase
import com.example.OnlineShop.domain.use_cases.Product.GetProductsUseCase
import com.example.OnlineShop.domain.use_cases.Product.ProductUseCases
import com.example.retrofit.data.remote.OnlineShopApi
import com.example.OnlineShop.data.interceptor.CookieInterceptor
import com.example.retrofit.data.repositoryImpl.AuthRepositoryImpl
import com.example.retrofit.domain.repository.AuthRepository
import com.example.OnlineShop.domain.use_cases.Auth.IsLoggedInUseCase
import com.example.OnlineShop.domain.use_cases.Auth.LogOutUseCase
import com.example.OnlineShop.domain.use_cases.Auth.LoginUseCase
import com.example.OnlineShop.domain.use_cases.Auth.RegisterByUseCase
import com.example.OnlineShop.domain.use_cases.Auth.UseCases
import com.example.retrofit.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Singleton
    @Provides
    fun provideCookieInterceptor(@ApplicationContext context: Context): Interceptor {
        return CookieInterceptor(context)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(cookieInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(cookieInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun retrofitBuilder(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): OnlineShopApi {
        return retrofit.create(OnlineShopApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(api: OnlineShopApi): AuthRepository {
        return AuthRepositoryImpl(api)
    }
    @Singleton
    @Provides
    fun provideProductRepository(api: OnlineShopApi):ProductRepository{
        return ProductRepositoryImpl(api)
    }
    @Singleton
    @Provides
    fun provideCartRepository(api:OnlineShopApi):CartRepository{
        return CartRepositoryImpl(api)
    }
    @Singleton
    @Provides
    fun provideCheckoutRepository(api:OnlineShopApi):CheckoutRepository{
        return CheckoutRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideUseCases(repository: AuthRepository): UseCases {
        return UseCases(
            registerByUseCase = RegisterByUseCase(repository),
            LoginUseCase = LoginUseCase(repository),
            IsLoggedInUseCase = IsLoggedInUseCase(repository),
            LogOutUseCase = LogOutUseCase(repository)
        )
    }
    @Singleton
    @Provides
    fun provideProductUseCases(repository: ProductRepository):ProductUseCases {
        return ProductUseCases(
            getCategoriesUseCase = GetCategoriesUseCase(repository),
            getProductsUseCase = GetProductsUseCase(repository),
            getProductUseCase = GetProductUseCase(repository),
            getProductReviewsUseCase = GetProductReviewsUseCase(repository),
            addProductReviewUseCase = AddProductReviewUseCase(repository)
        )
    }
    @Singleton
    @Provides
    fun provideCartUseCases(repository:CartRepository):CartUseCases {
        return CartUseCases(
            addOrUpdateProductsToCartUseCase = AddOrUpdateProductsToCartUseCase(repository),
            getUserCartDataUseCase = GetUserCartDataUseCase(repository),
            deleteProductFromCartUseCase = DeleteProductFromCartUseCase(repository)
        )
    }
    @Singleton
    @Provides
    fun provideCheckoutUseCases(repository:CheckoutRepository):CheckoutUseCases {
        return CheckoutUseCases(
            getShippingInfoUseCase = GetShippingInfoUseCase(repository),
            orderUseCase = OrderUseCase(repository),
            getUserOrderHistoryUseCase = GetUserOrderHistoryUseCase(repository),
            getUserOrderedProducts = GetUserOrderedProducts(repository)
        )
    }
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }
}