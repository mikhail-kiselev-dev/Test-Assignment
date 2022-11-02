package com.ambiws.testassignment.core.di

import com.ambiws.testassignment.MainViewModel
import com.ambiws.testassignment.base.ui.EMPTY_SCOPE_NAME
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val coreModule = module {
    scope(EMPTY_SCOPE_NAME) {
        // Nothing to inject
    }
    viewModel {
        MainViewModel()
    }
}
