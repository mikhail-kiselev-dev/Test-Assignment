package com.ambiws.testassignment.core.di

import com.ambiws.testassignment.base.ui.EmptyViewModel
import com.ambiws.testassignment.core.view.CustomSnackbar
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val utilsModule = module {
    viewModel { EmptyViewModel() }
    factory { CustomSnackbar(get()) }
}
