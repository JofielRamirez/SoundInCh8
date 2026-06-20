package com.fixnow.soundinch8.ui.screens

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterViewModel : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    private val _nameError = MutableStateFlow(false)
    val nameError: StateFlow<Boolean> = _nameError.asStateFlow()

    private val _emailError = MutableStateFlow(false)
    val emailError: StateFlow<Boolean> = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow(false)
    val passwordError: StateFlow<Boolean> = _passwordError.asStateFlow()

    private val _confirmPasswordError = MutableStateFlow(false)
    val confirmPasswordError: StateFlow<Boolean> = _confirmPasswordError.asStateFlow()

    fun onNameChange(value: String) {
        _name.value = value
        _nameError.value = false
    }

    fun onEmailChange(value: String) {
        _email.value = value
        _emailError.value = false
    }

    fun onPasswordChange(value: String) {
        _password.value = value
        _passwordError.value = false
    }

    fun onConfirmPasswordChange(value: String) {
        _confirmPassword.value = value
        _confirmPasswordError.value = false
    }

    fun isFormEmpty(): Boolean {
        return _name.value.isBlank() &&
                _email.value.isBlank() &&
                _password.value.isBlank() &&
                _confirmPassword.value.isBlank()
    }

    fun validateAndRegister(): Boolean {
        val isNameInvalid = _name.value.isBlank()
        val isEmailInvalid = !_email.value.contains("@") || !_email.value.contains(".")
        val isPasswordInvalid = _password.value.length < 6
        val isConfirmPasswordInvalid = _confirmPassword.value != _password.value || _confirmPassword.value.isBlank()

        _nameError.value = isNameInvalid
        _emailError.value = isEmailInvalid
        _passwordError.value = isPasswordInvalid
        _confirmPasswordError.value = isConfirmPasswordInvalid

        return !isNameInvalid &&
                !isEmailInvalid &&
                !isPasswordInvalid &&
                !isConfirmPasswordInvalid
    }
}