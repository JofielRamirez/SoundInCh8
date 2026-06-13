package com.fixnow.soundinch8.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onGoBack: () -> Unit,
    registerViewModel: RegisterViewModel = viewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var showExitDialog by remember { mutableStateOf(false) }

    fun tryGoBack() {
        if (registerViewModel.isFormEmpty()) {
            onGoBack()
        } else {
            showExitDialog = true
        }
    }

    BackHandler(enabled = !registerViewModel.isFormEmpty()) {
        showExitDialog = true
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = {
                showExitDialog = false
            },
            title = {
                Text("Leave screen?")
            },
            text = {
                Text("You have unsaved data. Are you sure you want to go back?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showExitDialog = false
                        onGoBack()
                    }
                ) {
                    Text("Go Back")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showExitDialog = false
                    }
                ) {
                    Text("Stay")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Account") },
                navigationIcon = {
                    IconButton(onClick = { tryGoBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        RegisterContent(
            paddingValues = paddingValues,
            registerViewModel = registerViewModel,
            onRegisterClick = {
                val isValid = registerViewModel.validateAndRegister()

                if (isValid) {
                    scope.launch {
                        snackbarHostState.showSnackbar("Account created successfully")
                    }
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar("Please complete all fields correctly")
                    }
                }
            }
        )
    }
}

@Composable
fun RegisterContent(
    paddingValues: PaddingValues,
    registerViewModel: RegisterViewModel,
    onRegisterClick: () -> Unit
) {
    val name by registerViewModel.name.collectAsStateWithLifecycle()
    val email by registerViewModel.email.collectAsStateWithLifecycle()
    val password by registerViewModel.password.collectAsStateWithLifecycle()
    val confirmPassword by registerViewModel.confirmPassword.collectAsStateWithLifecycle()

    val nameError by registerViewModel.nameError.collectAsStateWithLifecycle()
    val emailError by registerViewModel.emailError.collectAsStateWithLifecycle()
    val passwordError by registerViewModel.passwordError.collectAsStateWithLifecycle()
    val confirmPasswordError by registerViewModel.confirmPasswordError.collectAsStateWithLifecycle()

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { registerViewModel.onNameChange(it) },
            label = { Text("Full Name") },
            isError = nameError,
            supportingText = {
                if (nameError) {
                    Text("Name is required")
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { registerViewModel.onEmailChange(it) },
            label = { Text("Email Address") },
            isError = emailError,
            supportingText = {
                if (emailError) {
                    Text("Enter a valid email address")
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { registerViewModel.onPasswordChange(it) },
            label = { Text("Password") },
            isError = passwordError,
            supportingText = {
                if (passwordError) {
                    Text("Minimum 6 characters")
                }
            },
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) {
                            Icons.Default.Visibility
                        } else {
                            Icons.Default.VisibilityOff
                        },
                        contentDescription = if (passwordVisible) {
                            "Hide Password"
                        } else {
                            "Show Password"
                        }
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { registerViewModel.onConfirmPasswordChange(it) },
            label = { Text("Confirm Password") },
            isError = confirmPasswordError,
            supportingText = {
                if (confirmPasswordError) {
                    Text("Passwords do not match")
                }
            },
            visualTransformation = if (confirmPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(
                        imageVector = if (confirmPasswordVisible) {
                            Icons.Default.Visibility
                        } else {
                            Icons.Default.VisibilityOff
                        },
                        contentDescription = if (confirmPasswordVisible) {
                            "Hide Password"
                        } else {
                            "Show Password"
                        }
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Account")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterContentPreview() {
    RegisterContent(
        paddingValues = PaddingValues(0.dp),
        registerViewModel = RegisterViewModel(),
        onRegisterClick = {}
    )
}