package com.example.airbarchallenge.di

import com.example.airbarchallenge.BuildConfig
import com.example.airbarchallenge.utils.Constants.API_QUERY_PARAM
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class InterceptorTMDB @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val originalUrl = chain.request().url
        val url = originalUrl.newBuilder().addQueryParameter(API_QUERY_PARAM, BuildConfig.API_KEY).build()
        request.url(url)
        return chain.proceed(request.build())
    }


}