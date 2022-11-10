package com.ambiws.testassignment.features.users.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ambiws.testassignment.base.ui.BaseViewModel
import com.ambiws.testassignment.core.extensions.mutable
import com.ambiws.testassignment.features.posts.domain.PostInteractor
import com.ambiws.testassignment.features.posts.domain.model.Post
import com.ambiws.testassignment.features.posts.ui.args.PostsFragmentParams
import com.ambiws.testassignment.features.users.domain.UserInteractor
import com.ambiws.testassignment.features.users.ui.list.DividerItemModel
import com.ambiws.testassignment.features.users.ui.list.UserItemModel
import com.ambiws.testassignment.features.users.ui.list.UserListItemModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine

class UsersViewModel(
    private val userInteractor: UserInteractor,
    private val postInteractor: PostInteractor,
) : BaseViewModel() {

    val users: LiveData<List<UserListItemModel>> = MutableLiveData(emptyList())

    val posts: MutableList<Post> = mutableListOf()

    init {
        launch {
            loadingObservable.mutable().value = true

            val list = mutableListOf<UserListItemModel>()
            userInteractor.getUsers().combine(postInteractor.getPosts()) { usersFlow, postsFlow ->
                posts.addAll(postsFlow)
                usersFlow.map {
                    list.add(
                        UserItemModel(
                            albumId = it.albumId,
                            userId = it.userId,
                            name = it.name,
                            url = it.url,
                            thumbnailUrl = it.thumbnailUrl,
                            postsCount = postsFlow.filter { post -> post.userId == it.userId }.size,
                        )
                    )
                    list.add(
                        DividerItemModel()
                    )
                }
            }.collectLatest {
                loadingObservable.mutable().value = false
                updateUsers(list)
            }
        }
    }

    private fun updateUsers(list: List<UserListItemModel>) {
        users.mutable().value = list
    }

    fun showUserPosts(userId: Long, userImage: String) {
        navigate(
            UsersFragmentDirections.actionUsersFragmentToPostsFragment(
                PostsFragmentParams(
                    userImage = userImage,
                    posts = posts.filter { it.userId == userId },
                )
            )
        )
    }
}
