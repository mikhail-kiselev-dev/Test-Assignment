package com.ambiws.testassignment.features.posts.data.response

import com.ambiws.testassignment.core.extensions.getOrThrowDefaultError
import com.ambiws.testassignment.features.posts.domain.model.Post
import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("userId")
    val userId: Long?,
    @SerializedName("id")
    val id: Long?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("body")
    val body: String?,
)

fun PostResponse.toDomain() =
    Post(
        userId = userId.getOrThrowDefaultError("userId"),
        id = id.getOrThrowDefaultError("id"),
        title = title.getOrThrowDefaultError("title"),
        body = body.getOrThrowDefaultError("body"),
    )
