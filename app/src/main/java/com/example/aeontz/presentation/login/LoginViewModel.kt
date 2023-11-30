package com.example.aeontz.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aeontz.Constants
import com.example.aeontz.data.ApiRepositoryImpl
import com.example.aeontz.data.SharedPreferenceDataStore
import com.example.aeontz.domain.model.UserRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiRepositoryImpl: ApiRepositoryImpl,
    private val sharedPreferenceDataStore: SharedPreferenceDataStore
): ViewModel() {
    fun userAuthorization(userRequest: UserRequest){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = apiRepositoryImpl.userAuth(Constants.appKey, Constants.v, userRequest)
                withContext(Dispatchers.Main){
                    sharedPreferenceDataStore.putString("USER_TOKEN", token.response?.token)
                }
            }catch (e: Exception){
                e.localizedMessage?.let { Log.d("checkException", it) }
            }catch (e: IOException){
                e.localizedMessage?.let { Log.d("checkException", it) }
            }
        }
    }
    fun isUserHasToken(): Boolean{
        if(sharedPreferenceDataStore.getString("USER_TOKEN", "Default")?.isNotBlank() == true){
            if(sharedPreferenceDataStore.getString("USER_TOKEN", "Default")?.isNotEmpty() == true){
                return true
            }
        }
        return false
    }
}