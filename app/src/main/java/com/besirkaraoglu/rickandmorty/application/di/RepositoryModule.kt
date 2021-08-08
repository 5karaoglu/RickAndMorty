package com.besirkaraoglu.rickandmorty.application.di

import com.besirkaraoglu.rickandmorty.data.Repository
import com.besirkaraoglu.rickandmorty.data.RepositoryImpl
import com.besirkaraoglu.rickandmorty.data.remote.WebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        webService: WebService
    ): Repository = RepositoryImpl(webService)
}