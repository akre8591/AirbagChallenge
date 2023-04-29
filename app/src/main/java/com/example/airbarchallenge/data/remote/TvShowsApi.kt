package com.example.airbarchallenge.data.remote

import com.example.airbarchallenge.di.IoDispatcher
import com.example.airbarchallenge.domain.models.Result
import com.example.airbarchallenge.utils.ResultNetwork
import com.example.airbarchallenge.utils.getResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject

class TvShowsApi @Inject constructor(
    private val retrofit: Retrofit,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    private val endpoints: Endpoints by lazy {
        retrofit.create()
    }

    suspend fun getTopRatedShows(): ResultNetwork<Result> = withContext(dispatcher) {
        return@withContext getResult {
            endpoints.getTopRatedShows()
        }
    }

}