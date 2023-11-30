package com.example.aeontz.data

import com.example.aeontz.domain.ApiRepository
import com.example.aeontz.domain.model.UserRequest
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val apiRepository: ApiRepository
): ApiRepository {
    override suspend fun userAuth(
        appKey: String,
        v: String,
        userRequest: UserRequest
    ) = apiRepository.userAuth(appKey, v, userRequest)

     override suspend fun getPaymentsAuth(
         appKey: String,
         v: String,
         token: String
     ) = apiRepository.getPaymentsAuth(appKey, v, token)
}