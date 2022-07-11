package com.danielpasser.posts.di

import com.danielpasser.posts.network.Api
import com.danielpasser.posts.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder().baseUrl(Constants.BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptorLogger = HttpLoggingInterceptor()
        interceptorLogger.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptorLogger).build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit.Builder): Api = retrofit.build().create(Api::class.java)

}