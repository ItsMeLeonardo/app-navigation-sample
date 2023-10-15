package com.example.partial.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


class OtpViewModel : ViewModel() {
    private val generatedOtp = (100000..999999).random().toString()

    val enteredOtp = mutableStateOf("")

    fun isOtpValid(): Boolean {
        Log.d("OTP VERIFY", "Entered OTP: ${enteredOtp.value}  --- Generated OTP: $generatedOtp")

        return enteredOtp.value == generatedOtp
    }

    fun getGeneratedOtp(): String {
        return generatedOtp
    }
}
