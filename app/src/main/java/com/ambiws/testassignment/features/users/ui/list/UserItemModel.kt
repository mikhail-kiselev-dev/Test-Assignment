package com.ambiws.testassignment.features.users.ui.list

import com.ambiws.testassignment.features.posts.domain.model.Post

data class UserItemModel(
    val albumId: Long,
    val userId: Long,
    val name: String,
    val url: String,
    val thumbnailUrl: String,
    val postsCount: Int,
    val posts: List<Post>,
) : UserListItemModel
