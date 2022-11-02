package com.ambiws.testassignment.features.users.domain

import com.ambiws.testassignment.features.users.data.UserRepository
import com.ambiws.testassignment.features.users.domain.model.User
import kotlinx.coroutines.flow.StateFlow

class UserInteractorImpl(
    private val repository: UserRepository,
) : UserInteractor {

    override val users: StateFlow<List<User>>
        get() = repository.usersFlow

    override suspend fun getUsers() {
        repository.getUsers()
    }
}
