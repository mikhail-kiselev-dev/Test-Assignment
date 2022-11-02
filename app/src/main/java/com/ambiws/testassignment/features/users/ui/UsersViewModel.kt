package com.ambiws.testassignment.features.users.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ambiws.testassignment.base.ui.BaseViewModel
import com.ambiws.testassignment.core.extensions.mutable
import com.ambiws.testassignment.features.users.domain.UserInteractor
import com.ambiws.testassignment.features.users.domain.model.User
import com.ambiws.testassignment.features.users.domain.model.toItemModel
import com.ambiws.testassignment.features.users.ui.list.UserItemModel
import kotlinx.coroutines.flow.collectLatest

class UsersViewModel(
    private val userInteractor: UserInteractor,
) : BaseViewModel() {

    val users: LiveData<List<UserItemModel>> = MutableLiveData(emptyList())

    init {
        launch {
            userInteractor.getUsers()
            userInteractor.users.collectLatest {
                updateUsers(it)
            }
        }
    }

    private fun updateUsers(list: List<User>) {
        users.mutable().value = list.map {
            it.toItemModel()
        }
    }
}
