package com.ambiws.testassignment.core.view

import android.view.View
import com.ambiws.testassignment.R
import com.ambiws.testassignment.core.providers.ResourceProvider
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class CustomSnackbar(private val resourceProvider: ResourceProvider) {

    private var snackbar: Snackbar? = null

    fun showCustomSnackBar(parentView: View, message: String) {
        hideCustomSnackBar()
        snackbar = Snackbar.make(parentView, message, Snackbar.LENGTH_SHORT).apply {
            setTextColor(resourceProvider.getColor(R.color.red_snackbar_error))
        }

        snackbar?.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        snackbar?.show()
    }

    private fun hideCustomSnackBar() {
        snackbar?.dismiss()
        snackbar = null
    }
}
