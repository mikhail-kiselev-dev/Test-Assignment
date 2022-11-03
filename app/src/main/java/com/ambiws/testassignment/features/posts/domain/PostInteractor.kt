package com.ambiws.testassignment.features.posts.domain

import com.ambiws.testassignment.features.posts.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostInteractor {
    suspend fun getPosts() : Flow<List<Post>>
    suspend fun getUserPosts(id: Long) : Flow<List<Post>>
}
