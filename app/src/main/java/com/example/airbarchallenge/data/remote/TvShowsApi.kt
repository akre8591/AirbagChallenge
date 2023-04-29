package com.example.airbarchallenge.data.remote

import com.example.airbarchallenge.domain.models.TvShow
import com.example.airbarchallenge.utils.ResultNetwork
import com.example.airbarchallenge.utils.getResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject

class TvShowsApi @Inject constructor(
    private val retrofit: Retrofit,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private val endpoints: Endpoints by lazy {
        retrofit.create()
    }

    suspend fun getTopRatedShows(): ResultNetwork<List<TvShow>> = withContext(dispatcher) {
        return@withContext getResult { endpoints.getTopRatedShows() }
    }

}