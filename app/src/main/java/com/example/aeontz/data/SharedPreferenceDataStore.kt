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

    init {
        sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun putString(key: String, value: String?){
        editor.putString(key,value).apply()
    }

    fun getString(key: String, value: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun clear(key: String){
        sharedPreferences.edit().remove(key).apply()
    }
}