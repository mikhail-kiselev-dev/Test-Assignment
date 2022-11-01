package com.ambiws.testassignment.core.di

import com.ambiws.testassignment.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val coreModule = module {
    viewModel {
        MainViewModel()
    }
}