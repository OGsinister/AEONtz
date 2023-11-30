package com.example.aeontz.domain

import com.example.aeontz.domain.model.Payment
import com.example.aeontz.domain.model.TokenResponse
import com.example.aeontz.domain.model.UserRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiRepository {
    @POST("login")
    suspend fun userAuth(
        @Header("app-key") appKey: String,
        @Header("v") v: String,
        @Body userRequest: UserRequest
    ): TokenResponse

    @GET("payments")
    @Headers("Content-Type: application/json")
    suspend fun getPaymentsAuth(
        @Header("app-key") appKey: String,
        @Header("v") v: String,
        @Header("token") token: String
    ): Payment
}