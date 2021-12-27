package com.example.randomuser

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object APIClient {
    private var retrofit: Retrofit? = null
    val client: Retrofit?
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
            retrofit = Retrofit.Builder()
                .baseUrl("https://randomuser.me/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit
        }

    @JvmStatic
    fun ATservice(): AccessTokenServiceInterface {
        return client!!.create(
            AccessTokenServiceInterface::class.java
        )
    }
}