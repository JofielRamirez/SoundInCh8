package com.example.soundinch8.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel: ViewModel() {
    // Private state only this class can write

    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _rememberSession = MutableStateFlow(false)
    private val _emailError = MutableStateFlow(false)
    private val _passwordError = MutableStateFlow(false)

    // Public State-The UI observes these, read-only
    val email: StateFlow<String> = _email.asStateFlow()
    val password: StateFlow<String> = _password.asStateFlow()
    val rememberSession : StateFlow<Boolean> = _rememberSession.asStateFlow()
    val emailError : StateFlow<Boolean> = _emailError.asStateFlow()
    val passwordError: StateFlow<Boolean> = _passwordError.asStateFlow()

    fun onEmailChanged(value : String){
        _email.value = value
        _emailError.value = false // clear error as soon as user types again
    }

    fun onPasswordChanged(value: String){
        _password.value = value
        _passwordError.value = false
    }

    fun onRememberSessionChanged(value: Boolean){
        _rememberSession.value = value
    }

    fun validateAndLogin(): Boolean {
        val isEmailValid = _email.value.contains("@") && _email.value.contains(".")
        val isPasswordValid = _password.value.length >= 6
        return  isEmailValid && isPasswordValid
    }
}













