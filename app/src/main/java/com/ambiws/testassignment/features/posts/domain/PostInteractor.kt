package com.ambiws.testassignment.features.posts.domain

import com.ambiws.testassignment.features.posts.domain.model.Post
import kotlinx.coroutines.flow.StateFlow

interface PostInteractor {
    val postsFlow: StateFlow<List<Post>>
    suspend fun getPosts()
}
