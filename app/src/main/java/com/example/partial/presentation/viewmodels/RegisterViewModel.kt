package com.example.partial.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.partial.domain.SubscriptionType
import com.example.partial.domain.User
import com.example.partial.domain.repository.UserRepository
import com.example.partial.domain.security.hashPassword

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    val username = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")

    val registrationStatus = MutableLiveData<String>()

    fun register(): Boolean {

        val hashedPassword = hashPassword(password.value)

        Log.d("REGISTER", "Hashed password: $hashedPassword")
        val user = User(
            id = 0,
            username = username.value,
            email = email.value,
            password = hashedPassword,
            subscriptionType = SubscriptionType.FREE
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
