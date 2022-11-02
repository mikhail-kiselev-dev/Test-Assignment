package com.ambiws.testassignment.core.di

import com.ambiws.testassignment.BuildConfig
import com.ambiws.testassignment.core.network.error.parser.ErrorCallAdapterFactory
import com.ambiws.testassignment.features.users.network.UserApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    factory { GsonConverterFactory.create(get(named(GSON))) }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(get<GsonConverterFactory>())
            .addCallAdapterFactory(ErrorCallAdapterFactory())
            .client(get() as OkHttpClient)
            .build()
    }

    single<UserApi> {
        (get() as Retrofit).create(UserApi::class.java)
    }
}
