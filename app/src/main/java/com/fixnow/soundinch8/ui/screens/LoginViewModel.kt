package com.fixnow.soundinch8.ui.screens


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _rememberSession = MutableStateFlow(false)
    val rememberSession: StateFlow<Boolean> = _rememberSession.asStateFlow()

    private val _emailError = MutableStateFlow(false)
    val emailError: StateFlow<Boolean> = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow(false)
    val passwordError: StateFlow<Boolean> = _passwordError.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        _emailError.value = false
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        _passwordError.value = false
    }

    fun onRememberSessionChange(value: Boolean) {
        _rememberSession.value = value
    }

    fun validateAndLogin(): Boolean {
        val isEmailInvalid = !_email.value.contains("@") || !_email.value.contains(".")
        val isPasswordInvalid = _password.value.length < 6

        _emailError.value = isEmailInvalid
        _passwordError.value = isPasswordInvalid

        return !isEmailInvalid && !isPasswordInvalid
    }
}