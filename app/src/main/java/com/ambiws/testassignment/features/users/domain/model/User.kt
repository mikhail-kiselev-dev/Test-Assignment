package com.ambiws.testassignment.features.users.domain.model

import com.ambiws.testassignment.features.users.ui.list.UserItemModel

data class User(
    val albumId: Long,
    val userId: Long,
    val name: String,
    val body: String,
    val thumbnailUrl: String,
)

fun User.toItemModel() =
    UserItemModel(
        albumId = albumId,
        userId = userId,
        name = name,
        body = body,
        thumbnailUrl = thumbnailUrl,
    )
