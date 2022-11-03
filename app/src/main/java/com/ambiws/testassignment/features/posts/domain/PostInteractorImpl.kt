package com.ambiws.testassignment.features.posts.domain

import com.ambiws.testassignment.features.posts.data.PostRepository
import com.ambiws.testassignment.features.posts.domain.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PostInteractorImpl(
    private val repository: PostRepository
) : PostInteractor {

    override suspend fun getPosts() : Flow<List<Post>> {
        repository.getPosts()
        return repository.postsFlow
    }

    override suspend fun getUserPosts(id: Long) : Flow<List<Post>> {
        repository.getPosts()
        return repository.postsFlow.map {
            it.filter { post -> post.userId == id }
        }
    }
}
