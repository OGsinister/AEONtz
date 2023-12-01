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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiRepositoryImpl: ApiRepositoryImpl,
    private val sharedPreferenceDataStore: SharedPreferenceDataStore
): ViewModel() {

    private val _tokenCheck = MutableStateFlow<Boolean?>(null)
    var tokenCheck = _tokenCheck.asStateFlow()
    init {
        _tokenCheck.value = sharedPreferenceDataStore.contains()
    }
    fun userAuthorization(userRequest: UserRequest){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val tokenResponse = apiRepositoryImpl.userAuth(Constants.appKey, Constants.v, userRequest)

                withContext(Dispatchers.Main){
                    sharedPreferenceDataStore.putString(tokenResponse.response?.token)
                    _tokenCheck.value = sharedPreferenceDataStore.contains()
                }
            }catch (e: Exception){
                e.localizedMessage?.let { Log.d("checkException", it) }
            }catch (e: IOException){
                e.localizedMessage?.let { Log.d("checkException", it) }
            }
        }
    }
    fun changeTokenValue(newValue: Boolean){
        _tokenCheck.value = newValue
    }
}