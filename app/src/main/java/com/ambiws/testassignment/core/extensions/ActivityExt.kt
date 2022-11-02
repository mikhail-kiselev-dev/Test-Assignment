package com.ambiws.testassignment.core.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity
import org.koin.androidx.viewmodel.ViewModelOwner

fun Activity.hideKeyboard(v: View? = null) {
    val view = v ?: this.currentFocus ?: findViewById(android.R.id.content)
    view?.also {
        val imm = it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Activity.showKeyboard(view: View? = currentFocus) {
    view?.also {
        it.requestFocus()
        it.performClick()
        val imm =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, 0)
    }
}

fun Activity.getRootView(): View {
    return findViewById(android.R.id.content)
}

fun FragmentActivity.asOwnerDefinition(): () -> ViewModelOwner = { ViewModelOwner.from(this, this) }
