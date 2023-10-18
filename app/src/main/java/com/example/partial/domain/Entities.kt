package com.example.partial.domain

data class Patient(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val age: Int
)

data class Doctor(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val specialty: String
)

enum class SubscriptionType {
    FREE,
    PREMIUM
}

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val subscriptionType: SubscriptionType
)