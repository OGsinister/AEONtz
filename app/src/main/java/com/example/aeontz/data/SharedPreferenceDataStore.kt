package com.example.aeontz.data

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferenceDataStore @Inject constructor(
   @ApplicationContext context: Context
){
    private val preferenceName = "sharedPref"
    private var sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor

    companion object{
        private const val TOKEN_KEY = "USER_TOKEN"
    }

    init {
        sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun putString(value: String?){
        editor.putString(TOKEN_KEY,value).commit()
    }

   /* fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
        //return sharedPreferences.getString(TOKEN_KEY, null)
    }*/

    fun getToken(): String?{
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    fun contains(): Boolean {
        return sharedPreferences.contains(TOKEN_KEY)
    }

    fun clear(){
        //sharedPreferences.edit().remove(TOKEN_KEY).apply()
        sharedPreferences.edit().clear().apply()
    }
}