package com.ambiws.testassignment.features.users.domain.model

data class User(
    val albumId: Long,
    val userId: Long,
    val name: String,
    val body: String,
    val thumbnailUrl: String,
)
