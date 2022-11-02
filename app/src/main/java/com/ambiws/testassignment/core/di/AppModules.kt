package com.ambiws.testassignment.core.di

import com.ambiws.testassignment.features.users.di.userModule

object AppModules {
    val applicationModules = listOf(
        coreModule,
        providersModule,
        utilsModule,
        networkModule,
        userModule,
    )
}
