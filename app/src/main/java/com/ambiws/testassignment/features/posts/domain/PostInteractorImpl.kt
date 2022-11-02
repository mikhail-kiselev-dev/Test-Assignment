package com.ambiws.testassignment.features.posts.domain

import com.ambiws.testassignment.features.posts.data.PostRepository
import com.ambiws.testassignment.features.posts.domain.model.Post
import kotlinx.coroutines.flow.StateFlow

class PostInteractorImpl(
    private val repository: PostRepository
) : PostInteractor {

    override val postsFlow: StateFlow<List<Post>>
        get() = repository.postsFlow

    override suspend fun getPosts() {
        repository.getPosts()
    }
}
