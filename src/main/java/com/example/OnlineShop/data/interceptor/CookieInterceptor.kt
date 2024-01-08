package com.example.OnlineShop.data.interceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpCookie

class CookieInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Retrieve cookies from storage
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)



        val storedAccessToken = sharedPreferences.getString("access_token", "")
        val fullname = sharedPreferences.getString("fullname", "")
        val newRequest = if (storedAccessToken?.isNotEmpty() == true) {
            request.newBuilder()
                .header("Cookie", "access_token=$storedAccessToken")
                .build()
        } else {
            request
        }

        val response = chain.proceed(newRequest)

        // Extract access token from the response and save it
        if (request.url().toString().endsWith("Login")) {
            val cookies = response.headers("Set-Cookie")
            val accessTokenCookie = cookies.find { it.startsWith("access_token=") }

            if (accessTokenCookie != null) {
                val httpCookie = HttpCookie.parse(accessTokenCookie).firstOrNull()

                if (httpCookie != null) {
                    val editor = sharedPreferences.edit()
                    editor.putString("access_token", httpCookie.value)
                    editor.apply()
                }
            }
        }

        return response
    }

}
