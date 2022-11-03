package com.ambiws.testassignment.features.posts.di

import com.ambiws.testassignment.features.posts.data.PostRepository
import com.ambiws.testassignment.features.posts.data.PostRepositoryImpl
import com.ambiws.testassignment.features.posts.domain.PostInteractor
import com.ambiws.testassignment.features.posts.domain.PostInteractorImpl
import com.ambiws.testassignment.features.posts.ui.PostsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val postModule = module {
    single<PostRepository> { PostRepositoryImpl(get()) }
    factory<PostInteractor> { PostInteractorImpl(get()) }
    viewModel {
        PostsViewModel(get())
    }
}
