package com.ambiws.testassignment.features.posts.domain.model

import com.ambiws.testassignment.features.posts.ui.list.PostItemModel

data class Post(
    val userId: Long,
    val id: Long,
    val title: String,
    val body: String,
)

fun Post.toItemModel() =
    PostItemModel(
        title = title,
        body = body,
    )
