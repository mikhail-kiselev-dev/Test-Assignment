package com.ambiws.testassignment.features.users.di

import com.ambiws.testassignment.features.users.data.UserRepository
import com.ambiws.testassignment.features.users.data.UserRepositoryImpl
import com.ambiws.testassignment.features.users.domain.UserInteractor
import com.ambiws.testassignment.features.users.domain.UserInteractorImpl
import com.ambiws.testassignment.features.users.ui.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    factory<UserInteractor> { UserInteractorImpl(get()) }
    viewModel {
        UsersViewModel(get(), get())
    }
}
