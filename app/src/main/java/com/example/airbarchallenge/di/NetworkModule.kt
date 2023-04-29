package com.example.airbarchallenge.di

import com.example.airbarchallenge.BuildConfig
import com.example.airbarchallenge.utils.Constants.TIME_OUT_IN_SECONDS
import com.example.airbarchallenge.utils.InterceptorTMDB
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(TIME_OUT_IN_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT_IN_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_IN_SECONDS, TimeUnit.SECONDS).also { client ->
                if (BuildConfig.DEBUG) {
                    val loggingInterceptor = HttpLoggingInterceptor()
                    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    client.addInterceptor(loggingInterceptor)
                }
            }.build()
    }

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor {
        return InterceptorTMDB()
    }

}