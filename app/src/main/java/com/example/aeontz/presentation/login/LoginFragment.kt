package com.example.aeontz.presentation.login

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
import com.example.aeontz.Constants
import com.example.aeontz.R
import com.example.aeontz.databinding.FragmentLoginBinding
import com.example.aeontz.domain.model.UserRequest
import com.example.aeontz.presentation.showErrorToastMessage
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                loginViewModel.tokenCheck
                    .collect{
                        if(it == true){
                            findNavController().navigate(R.id.action_LoginFragment_to_PaymentsFragment)
                        }
                    }
            }
        }

        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            buttonLogin.setOnClickListener{
                if(isFieldEmpty(LoginEditText.text.toString(), passwordEditText.text.toString())){
                    if(isFieldsCorrect(LoginEditText.text.toString(), passwordEditText.text.toString())){
                        loginViewModel.userAuthorization(
                            userRequest = UserRequest(
                                login = binding.LoginEditText.text.toString(),
                                password = binding.passwordEditText.text.toString()
                            )
                        )
                        //findNavController().navigate(R.id.action_LoginFragment_to_PaymentsFragment)
                    }else {
                        showErrorToastMessage(requireActivity().baseContext, "Неверные данные")
                    }
                }else {
                    showErrorToastMessage(requireActivity().baseContext, "Неверные данные")
                }
            }
        }
    }

    private fun isFieldsCorrect(editText: String, passwordText: String): Boolean {
        if(editText == Constants.correctLogin && passwordText == Constants.correctPassword)
            return true
        return false
    }

    private fun isFieldEmpty(editText: String, passwordText: String): Boolean {
        if(editText.isNotEmpty() && passwordText.isNotEmpty())
            return true
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}