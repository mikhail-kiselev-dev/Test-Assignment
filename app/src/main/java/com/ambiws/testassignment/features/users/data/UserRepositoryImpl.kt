package com.ambiws.testassignment.features.users.data

import com.ambiws.testassignment.features.users.data.response.toDomain
import com.ambiws.testassignment.features.users.domain.model.User
import com.ambiws.testassignment.features.users.network.UserApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserRepositoryImpl(
    private val api: UserApi
) : UserRepository {

    private val _usersFlow: MutableStateFlow<List<User>> =
        MutableStateFlow(emptyList())

    override val usersFlow: StateFlow<List<User>>
        get() = _usersFlow

    override suspend fun getUsers() {
        _usersFlow.value = api.getUsers().map {
            it.toDomain()
        }
    }
}
