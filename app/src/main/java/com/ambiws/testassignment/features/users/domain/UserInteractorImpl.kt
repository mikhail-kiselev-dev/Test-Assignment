package com.ambiws.testassignment.features.users.domain

import com.ambiws.testassignment.features.users.data.UserRepository
import com.ambiws.testassignment.features.users.domain.model.User
import kotlinx.coroutines.flow.Flow

class UserInteractorImpl(
    private val repository: UserRepository,
) : UserInteractor {

    override suspend fun getUsers() : Flow<List<User>> {
        repository.getUsers()
        return repository.usersFlow
    }
}
