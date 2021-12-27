package com.example.randomuser


import com.example.randomuser.model.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AccessTokenServiceInterface {

    @GET("?results=50")
    fun getRandomUser() : Call<User>


}