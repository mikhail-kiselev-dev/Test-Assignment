package com.ambiws.testassignment.features.posts.ui.args

import android.os.Parcelable
import com.ambiws.testassignment.features.posts.domain.model.Post
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostsFragmentParams(
    val userImage: String,
    val posts: List<Post>,
) : Parcelable
