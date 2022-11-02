package com.ambiws.testassignment.features.posts.data

import com.ambiws.testassignment.features.posts.domain.model.Post
import kotlinx.coroutines.flow.StateFlow

interface PostRepository {
    val postsFlow: StateFlow<List<Post>>
    suspend fun getPosts()
}
