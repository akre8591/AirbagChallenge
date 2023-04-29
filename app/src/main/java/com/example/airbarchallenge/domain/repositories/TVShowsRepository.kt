package com.example.airbarchallenge.domain.repositories

import com.example.airbarchallenge.domain.models.TvShow
import com.example.airbarchallenge.utils.ResultRepository
import kotlinx.coroutines.flow.Flow

interface TVShowsRepository {

    fun getTopRatedShows(): Flow<ResultRepository<List<TvShow>>>

}