package com.ambiws.testassignment.base.navigation

import android.app.Activity
import androidx.navigation.NavController
import com.ambiws.testassignment.core.extensions.hideKeyboard
import com.ambiws.testassignment.core.utils.logd

typealias NavControllerDefinition = () -> NavController

open class NavigationCommandHandlerImpl(
    private val navControllerDefinition: NavControllerDefinition,
    private val childNavControllerDefinition: NavControllerDefinition? = null
) : NavigationCommandHandler {

    override fun handle(
        activity: Activity,
        navigationCommand: NavigationCommand
    ) {
        if (navigationCommand.hideKeyboard) {
            activity.hideKeyboard()
        }
        try {
            val navController = navControllerDefinition.invoke()
            handle(activity, navController, navigationCommand)
        } catch (e: IllegalArgumentException) {
            logd("Error happened while trying to dispatch navigation command, $e")
        }
    }

    private fun handle(
        activity: Activity,
        navController: NavController,
        navigationCommand: NavigationCommand
    ) {
        when (navigationCommand) {
            is NavigationCommand.ToUri -> {
                navController.navigate(
                    navigationCommand.uri
                )
            }
            is NavigationCommand.To -> {
                navController.navigate(
                    navigationCommand.direction.actionId,
                    navigationCommand.direction.arguments,
                    navigationCommand.navOptions,
                    navigationCommand.navigationExtras
                )
            }
            is NavigationCommand.Back -> {
                navController.navigateUp()
            }
            is NavigationCommand.BackToStart -> {
                navController.popBackStack(navController.graph.startDestinationId, false)
            }
            is NavigationCommand.CompoundNavigationCommand -> {
                for (command in navigationCommand.navigationCommandList) {
                    handle(activity, navController, command)
                }
            }
            is NavigationCommand.HostNavigationCommand -> {
                childNavControllerDefinition?.invoke()?.also { controller ->
                    handle(activity, controller, navigationCommand.navigationCommand)
                } ?: throw IllegalStateException("Declare childNavControllerDefinition to handle HostNavigationCommand")
            }
        }
    }
}
