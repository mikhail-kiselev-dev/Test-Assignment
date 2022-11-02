package com.ambiws.testassignment.base.navigation

import android.net.Uri
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

/**
 * Implementation of command pattern used to communicate between ViewModel & Fragment
 */
sealed class NavigationCommand(open val hideKeyboard: Boolean) {

    data class ToUri(
        val uri: Uri,
        override val hideKeyboard: Boolean
    ) : NavigationCommand(hideKeyboard)

    data class To(
        val direction: NavDirections,
        val navigationExtras: Navigator.Extras? = null,
        override val hideKeyboard: Boolean = true,
        val navOptions: NavOptions? = null
    ) : NavigationCommand(hideKeyboard)

    data class Back(override val hideKeyboard: Boolean = true) : NavigationCommand(hideKeyboard)

    data class BackToStart(override val hideKeyboard: Boolean = true) :
        NavigationCommand(hideKeyboard)

    data class CompoundNavigationCommand(
        val navigationCommandList: List<NavigationCommand>,
        override val hideKeyboard: Boolean = true
    ) : NavigationCommand(hideKeyboard)

    /**
     * A navigation command that should be run for children
     */
    data class HostNavigationCommand(
        val navigationCommand: NavigationCommand,
        override val hideKeyboard: Boolean = navigationCommand.hideKeyboard
    ) : NavigationCommand(hideKeyboard = hideKeyboard)
}
