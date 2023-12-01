package com.example.aeontz.presentation.payments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aeontz.R
import com.example.aeontz.databinding.FragmentPaymentsBinding
import com.example.aeontz.domain.Resources
import com.example.aeontz.presentation.login.LoginViewModel
import com.example.aeontz.presentation.showErrorToastMessage
import kotlinx.coroutines.launch

class PaymentsFragment : Fragment() {
    private var _binding: FragmentPaymentsBinding? = null
    private val binding
        get() = _binding!!
    private val paymentsViewModel: PaymentsViewModel by activityViewModels()
    private val loginViewModel: LoginViewModel by activityViewModels()
    private var adapter: PaymentsAdapter? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentsBinding.inflate(inflater, container, false)
        val manager = LinearLayoutManager(activity)
        adapter = PaymentsAdapter()

        lifecycleScope.launch{
            loginViewModel.tokenCheck
                .collect{
                    if(it != true){
                        findNavController().navigate(R.id.action_PaymentsFragment_to_LoginFragment)
                    }
                }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                paymentsViewModel.getPayments().collect{ state ->
                    binding.apply {
                        when(state){
                            is Resources.Error -> {
                                progressBar.visibility = View.INVISIBLE
                                paymentsRecyclerView.visibility = View.INVISIBLE
                                state.message?.let {
                                    showErrorToastMessage(requireActivity().baseContext, it)
                                }
                            }
                            is Resources.Loading -> {
                                progressBar.visibility = View.VISIBLE
                                paymentsRecyclerView.visibility = View.INVISIBLE
                            }
                            is Resources.Success -> {
                                progressBar.visibility = View.INVISIBLE
                                paymentsRecyclerView.visibility = View.VISIBLE
                                state.data?.let {
                                    adapter?.payments = it
                                }
                                adapter?.notifyDataSetChanged()
                                binding.paymentsRecyclerView.layoutManager = manager
                                binding.paymentsRecyclerView.adapter = adapter
                            }
                        }
                        logoutButton.setOnClickListener{
                            logout()
                        }
                    }
                }
            }
        }
        return binding.root
    }

    private fun logout() {
        paymentsViewModel.clearToken()
        loginViewModel.changeTokenValue(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}