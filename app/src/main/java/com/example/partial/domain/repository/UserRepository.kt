package com.example.partial.domain.repository

import com.example.partial.domain.User

interface UserRepository {
    fun register(user: User): Boolean
    fun findByUsername(username: String): User?
}

class InMemoryUserRepository : UserRepository {

    private val users = mutableListOf<User>()

    override fun register(user: User): Boolean {
        users.add(user)
        return true
    }

    override fun findByUsername(username: String): User? {
        return users.find { it.username == username }
    }
}
