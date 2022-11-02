package com.ambiws.testassignment.core.di

import com.ambiws.testassignment.core.providers.PreferencesProvider
import com.ambiws.testassignment.core.providers.PreferencesProviderImpl
import com.ambiws.testassignment.core.providers.ResourceProvider
import com.ambiws.testassignment.core.providers.ResourceProviderImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val GSON = "gson"

val providersModule = module {
    single<PreferencesProvider> { PreferencesProviderImpl(androidContext()) }
    factory<ResourceProvider> { ResourceProviderImpl(androidContext()) }
    single<Gson>(named(GSON)) { GsonBuilder().serializeNulls().create() }
    single { get<ResourceProvider>().resources() }
}
