package com.example.partial.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    val username = mutableStateOf("")
    val password = mutableStateOf("")

    fun isValid(): Boolean {
        return username.value.isNotBlank() && password.value.isNotBlank()
    }

}