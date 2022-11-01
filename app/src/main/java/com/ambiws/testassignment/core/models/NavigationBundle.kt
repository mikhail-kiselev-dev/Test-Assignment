package com.ambiws.testassignment.core.models

import android.os.Bundle

data class NavigationBundle(
    val destinationId: Int,
    val bundle: Bundle? = null,
    val animation: AnimationType = AnimationType.NONE
)
