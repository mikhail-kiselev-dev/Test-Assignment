package com.ambiws.testassignment.features.users.ui.list

data class UserItemModel(
    val albumId: Long,
    val userId: Long,
    val name: String,
    val body: String,
    val thumbnailUrl: String,
) : UserListItemModel
