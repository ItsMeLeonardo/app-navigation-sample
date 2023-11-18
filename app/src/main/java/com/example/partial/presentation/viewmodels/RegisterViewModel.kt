package com.example.partial.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.partial.domain.SubscriptionType
import com.example.partial.domain.User
import com.example.partial.domain.repository.UserRepository
import com.example.partial.domain.security.hashPassword

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    val username = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")

    val registrationStatus = mutableStateOf<String>("")

    fun isPasswordSecure(password: String): Boolean {
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialCharacter = password.contains(Regex("[!@#$%^&*()-+=<>?]"))

        return password.length >= 8 && hasUpperCase && hasLowerCase && hasDigit && hasSpecialCharacter
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
        return email.matches(emailRegex)
    }

    fun register(): Boolean {
        if (username.value.isEmpty() || email.value.isEmpty() || password.value.isEmpty()) {
            registrationStatus.value = "Please fill all fields."
            return false
        }
        if (!isValidEmail(email.value)) {
            registrationStatus.value = "Please enter an email address."
            return false
        }


        val hashedPassword = hashPassword(password.value)

        Log.d("REGISTER", "Hashed password: $hashedPassword")
        val user = User(
            id = 0,
            username = username.value,
            email = email.value,
            password = hashedPassword,
            subscriptionType = SubscriptionType.PREMIUM
        )

        return if (userRepository.register(user)) {
            registrationStatus.value = "Registro exitoso!"
            true
        } else {
            registrationStatus.value = "Registro fallido."
            false
        }
    }
}
