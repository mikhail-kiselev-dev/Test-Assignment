package com.ambiws.testassignment.features.users.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ambiws.testassignment.base.ui.BaseViewModel
import com.ambiws.testassignment.core.extensions.mutable
import com.ambiws.testassignment.features.posts.domain.PostInteractor
import com.ambiws.testassignment.features.users.domain.UserInteractor
import com.ambiws.testassignment.features.users.ui.list.UserItemModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine

class UsersViewModel(
    private val userInteractor: UserInteractor,
    private val postInteractor: PostInteractor,
) : BaseViewModel() {

    val users: LiveData<List<UserItemModel>> = MutableLiveData(emptyList())

    init {
        launch {
            userInteractor.getUsers()
            postInteractor.getPosts()
            userInteractor.users.combine(postInteractor.postsFlow) { usersFlow, postsFlow ->
                usersFlow.map {
                    UserItemModel(
                        albumId = it.albumId,
                        userId = it.userId,
                        name = it.name,
                        body = it.body,
                        thumbnailUrl = it.thumbnailUrl,
                        postsCount = postsFlow.filter { post -> post.userId == it.userId }.size
                    )
                }
            }.collectLatest {
                updateUsers(it)
            }
        }
    }

    private fun updateUsers(list: List<UserItemModel>) {
        users.mutable().value = list
    }
}
