package com.example.partial.domain

import kotlinx.serialization.Serializable


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

@Serializable
data class Ingredient(
    val name: String,
    val obtained: Boolean = false
)

data class Recipe(
    val id: Int,
    val name: String,
    val ingredients: List<Ingredient>
)

data class RecipeDetail(
    val id: Int,
    val name: String,
    val description: String,
//    val ingredients: List<Ingredient>,
    val steps: List<String>,
    val time: Int,
    val kcal: Int,
)