package com.ambiws.testassignment.features.users.domain

import com.ambiws.testassignment.features.users.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserInteractor {
    suspend fun getUsers() : Flow<List<User>>
}
