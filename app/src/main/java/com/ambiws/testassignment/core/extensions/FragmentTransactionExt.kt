package com.ambiws.testassignment.core.extensions

import androidx.fragment.app.FragmentTransaction
import com.ambiws.testassignment.core.models.AnimationType

fun FragmentTransaction.setCustomAnimations(animation: AnimationType): FragmentTransaction {
    when (animation) {
        AnimationType.NONE -> {
            // Nothing to do
        }
    }
    return this
}
