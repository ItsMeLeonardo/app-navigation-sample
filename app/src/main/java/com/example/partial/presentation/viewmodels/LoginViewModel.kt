package com.example.partial.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.partial.data.userList
import com.example.partial.domain.security.checkPassword
import com.example.partial.domain.security.hashPassword

class LoginViewModel : ViewModel() {
    val username = mutableStateOf("")
    val password = mutableStateOf("")

    fun isValid(): Boolean {
        if (username.value.isEmpty() || password.value.isEmpty()) {
            return false
        }

        val user = userList.find { it.username == username.value } ?: return false
        return checkPassword(password.value, user.password)
    }

}