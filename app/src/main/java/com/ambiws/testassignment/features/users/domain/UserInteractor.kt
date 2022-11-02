package com.ambiws.testassignment.features.users.domain

import com.ambiws.testassignment.features.users.domain.model.User
import kotlinx.coroutines.flow.StateFlow

interface UserInteractor {
    val users: StateFlow<List<User>>
    suspend fun getUsers()
}
