package com.example.aeontz.presentation.payments

import androidx.lifecycle.ViewModel
import com.example.aeontz.Constants
import com.example.aeontz.data.SharedPreferenceDataStore
import com.example.aeontz.domain.ApiRepository
import com.example.aeontz.domain.Resources
import com.example.aeontz.domain.model.Payment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val sharedPreferenceDataStore: SharedPreferenceDataStore
): ViewModel(){
    fun getPayments(): Flow<Resources<Payment>> = flow {
        try{
            emit(Resources.Loading())

            val payments = apiRepository.getPaymentsAuth(
                appKey = Constants.appKey,
                v = Constants.v,
                token = sharedPreferenceDataStore.getToken()!!
            )

            emit(Resources.Success(payments))
        }catch (e: Exception){
            emit(Resources.Error(message = e.localizedMessage ?: "Неизвестная ошибка"))
        }catch (e: IOException){
            emit(Resources.Error(message = e.localizedMessage ?: "Неизвестная ошибка"))
        }
    }
    fun clearToken(){
        sharedPreferenceDataStore.clear()
    }
}