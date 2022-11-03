package com.ambiws.testassignment.features.posts.ui.args

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostsFragmentParams(
    val id: Long,
    val userImage: String,
) : Parcelable
