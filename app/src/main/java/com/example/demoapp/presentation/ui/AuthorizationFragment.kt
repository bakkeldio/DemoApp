package com.example.demoapp.presentation.ui

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.demoapp.R
import com.example.demoapp.databinding.AuthFragmentBinding
import com.example.demoapp.domain.ApiResult
import com.example.demoapp.extension.loadingHide
import com.example.demoapp.extension.loadingShow
import com.example.demoapp.extension.showSnackBar
import com.example.demoapp.presentation.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class AuthorizationFragment : Fragment(R.layout.auth_fragment) {

    private val viewModel by viewModels<WeatherViewModel>()
    private var _binding: AuthFragmentBinding? = null
    private var isValid = true

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AuthFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields()


        binding.login.setOnClickListener {
            validateFields()
            if (isValid){
                viewModel.login().observe(viewLifecycleOwner, {
                    when(it){
                        is ApiResult.Success->{
                            loadingHide()
                            val message = "Город: ${it.data.city}, Влажность: ${it.data.humidity}" +
                                    ", Облачность: ${it.data.cloudy}, Температура: ${it.data.temperature}"

                            showSnackBar(message)
                        }
                        is ApiResult.Error->{
                            loadingHide()
                            showSnackBar(it.data)
                        }
                        is ApiResult.Loading->{
                            loadingShow()
                        }
                        is ApiResult.NetworkError->{
                            loadingHide()
                            showSnackBar(it.data)
                        }
                        is ApiResult.EmptyResult->{
                            loadingHide()
                            showSnackBar(it.data)
                        }
                    }
                })
            }
        }
    }

    private fun validateFields() {
        val regex = ("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+\$")


        val pattern = Pattern.compile(regex)
        val password = binding.passwordText.text.toString()
        if (isValidEmail(binding.emailText.text.toString())) {
            isValid = true
        } else{
            binding.email.error = "Введите правильный email"
            isValid = false
        }

        if (password.isEmpty()) {
            isValid = false
            binding.password.error = "Пароль не может быть пустым"
        } else {
            Log.d("Password",password)
            if (pattern.matcher(password).matches() && password.length>=6) {
                if (isValid) {
                    isValid = true
                }
            }else{
                isValid = false
                binding.password.error =
                    "Пароль должен содержать минимум 6 символов: 1 строчную, 1 большую буквы и 1 цифру"
            }
        }
    }

    private fun initFields(){
        binding.passwordText.addTextChangedListener {
            binding.password.error = null
        }
        binding.emailText.addTextChangedListener {
            binding.email.error = null
        }
    }


    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}