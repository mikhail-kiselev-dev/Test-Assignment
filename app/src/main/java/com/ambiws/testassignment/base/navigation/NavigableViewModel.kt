package com.ambiws.testassignment.base.navigation

import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

interface NavigableViewModel {
    fun navigate(
        direction: NavDirections,
        navigatorExtras: Navigator.Extras? = null,
        hideKeyboard: Boolean = true,
        navOptions: NavOptions? = null
    )
}
