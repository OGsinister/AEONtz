package com.example.aeontz.presentation

import android.content.Context
import android.widget.Toast

fun showErrorToastMessage(context: Context,message: String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}