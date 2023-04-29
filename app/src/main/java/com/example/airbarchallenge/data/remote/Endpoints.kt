package com.example.airbarchallenge.data.remote

import com.example.airbarchallenge.domain.models.Result
import retrofit2.Response
import retrofit2.http.GET

interface Endpoints {

    @GET("tv/top_rated")
    suspend fun getTopRatedShows(): Response<Result>

}