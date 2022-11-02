package com.ambiws.testassignment.features.users.data

import com.ambiws.testassignment.features.users.domain.model.User
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    val usersFlow: StateFlow<List<User>>
    suspend fun getUsers()
}
