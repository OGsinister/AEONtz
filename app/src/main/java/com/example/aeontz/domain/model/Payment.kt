package com.example.aeontz.domain.model

data class Payment(
    val response: List<PaymentResponse>,
    val success: String? = null
)