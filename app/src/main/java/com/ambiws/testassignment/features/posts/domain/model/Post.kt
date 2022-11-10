package com.ambiws.testassignment.features.posts.domain.model

import android.os.Parcelable
import com.ambiws.testassignment.features.posts.ui.list.PostItemModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
    val userId: Long,
    val id: Long,
    val title: String,
    val body: String,
) : Parcelable

fun Post.toItemModel() =
    PostItemModel(
        title = title,
        body = body,
    )
