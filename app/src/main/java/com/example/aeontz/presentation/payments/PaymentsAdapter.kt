package com.example.aeontz.presentation.payments

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aeontz.databinding.PaymentItemBinding
import com.example.aeontz.domain.model.Payment
import com.example.aeontz.domain.model.PaymentResponse
class PaymentsAdapter: RecyclerView.Adapter<PaymentsAdapter.PaymentViewHolder>() {
    var payments: Payment = Payment(listOf(PaymentResponse()))
    class PaymentViewHolder(val binding: PaymentItemBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PaymentItemBinding.inflate(inflater, parent, false)

        return PaymentViewHolder(binding)
    }
    override fun getItemCount(): Int = payments.response.size
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val payment = payments.response[position]

        with(holder.binding){
            TextViewTitle.text = checkText(payment.title)
            TextViewCreated.text = checkText(payment.created)
            TextViewAmount.text = checkText(payment.amount)
        }
    }
    private fun checkText(text: String?): String{
        if(text?.isEmpty() == true || text == null) return "Нет данных"
        return text
    }
}