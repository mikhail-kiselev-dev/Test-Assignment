package com.ambiws.testassignment.features.posts.data

import com.ambiws.testassignment.features.posts.network.PostApi
import com.ambiws.testassignment.features.posts.data.response.toDomain
import com.ambiws.testassignment.features.posts.domain.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PostRepositoryImpl(
    private val api: PostApi
) : PostRepository {

    private val _postsFlow: MutableStateFlow<List<Post>> =
        MutableStateFlow(emptyList())

    override val postsFlow: StateFlow<List<Post>>
        get() = _postsFlow

    override suspend fun getPosts() {
        _postsFlow.value = api.getPosts().map {
            it.toDomain()
        }
    }
}
