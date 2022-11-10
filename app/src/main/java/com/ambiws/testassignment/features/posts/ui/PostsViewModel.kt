package com.ambiws.testassignment.features.posts.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ambiws.testassignment.base.ui.BaseViewModel
import com.ambiws.testassignment.core.extensions.mutable
import com.ambiws.testassignment.features.posts.domain.PostInteractor
import com.ambiws.testassignment.features.posts.domain.model.Post
import com.ambiws.testassignment.features.posts.domain.model.toItemModel
import com.ambiws.testassignment.features.posts.ui.list.PostItemModel
import kotlinx.coroutines.flow.collectLatest

class PostsViewModel(
    private val postsInteractor: PostInteractor
) : BaseViewModel() {

    val posts: LiveData<List<PostItemModel>> = MutableLiveData(emptyList())

    fun loadPosts(userId: Long, posts: List<Post>) {
        updateUserPosts(posts = posts.filter {
            it -> it.userId == userId
        })
    }

    private fun updateUserPosts(posts: List<Post>) {
        this.posts.mutable().value = posts.map {
            it.toItemModel()
        }
    }
}
