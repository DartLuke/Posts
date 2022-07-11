package com.danielpasser.posts.di

import com.danielpasser.posts.network.Api
import com.danielpasser.posts.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideCharacterRepository(retrofit: Api) = Repository(retrofit)
}