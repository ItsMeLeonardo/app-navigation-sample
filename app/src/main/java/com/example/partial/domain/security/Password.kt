package com.example.partial.domain.security

import org.mindrot.jbcrypt.BCrypt

fun hashPassword(plainTextPassword: String): String {
    return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt())
}

fun checkPassword(plainTextPassword: String, hashedPassword: String): Boolean {
    return BCrypt.checkpw(plainTextPassword, hashedPassword)
}


fun generateSecurePassword(
    length: Int = 12,

    ): String {
    val password = StringBuilder(length)
    val lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz"
    val upperCaseLetters = lowerCaseLetters.uppercase()
    val numbers = "0123456789"
    val specialCharacters = "!@#$%^&*()_-=+"
    val allowedCharacters = StringBuilder()
    allowedCharacters.append(lowerCaseLetters)
    allowedCharacters.append(upperCaseLetters)
    allowedCharacters.append(numbers)
    allowedCharacters.append(specialCharacters)

    for (i in 0 until length) {
        val randomIndex = (allowedCharacters.indices).random()
        password.append(allowedCharacters[randomIndex])
    }
    return password.toString()
}
