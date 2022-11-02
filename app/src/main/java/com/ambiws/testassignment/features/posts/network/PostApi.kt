package com.ambiws.testassignment.features.posts.network

import com.ambiws.testassignment.core.utils.Constants.API_PATH
import com.ambiws.testassignment.features.posts.data.response.PostResponse
import retrofit2.http.GET

private const val POSTS = "posts"

interface PostApi {

    @GET(API_PATH + POSTS)
    suspend fun getPosts(): List<PostResponse>
}
