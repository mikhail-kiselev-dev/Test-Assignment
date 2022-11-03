package com.ambiws.testassignment.features.users.data.response

import com.ambiws.testassignment.core.extensions.getOrThrowDefaultError
import com.ambiws.testassignment.features.users.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("albumId")
    val albumId: Long?,
    @SerializedName("userId")
    val userId: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String?,
)

fun UserResponse.toDomain() =
    User(
        albumId = albumId.getOrThrowDefaultError("albumId"),
        userId = userId.getOrThrowDefaultError("userId"),
        name = name.getOrThrowDefaultError("name"),
        url = url.getOrThrowDefaultError("url"),
        thumbnailUrl = thumbnailUrl.getOrThrowDefaultError("thumbnailUrl"),
    )
