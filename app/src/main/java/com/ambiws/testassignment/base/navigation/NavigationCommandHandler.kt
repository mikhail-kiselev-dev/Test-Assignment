package com.ambiws.testassignment.base.navigation

import android.app.Activity

interface NavigationCommandHandler {
    fun handle(
        activity: Activity,
        navigationCommand: NavigationCommand
    )
}
