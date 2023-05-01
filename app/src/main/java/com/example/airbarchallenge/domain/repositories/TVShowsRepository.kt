package com.example.airbarchallenge.domain.repositories

import com.example.airbarchallenge.data.db.ShowEntity
import com.example.airbarchallenge.domain.models.Result
import com.example.airbarchallenge.utils.ResultRepository
import kotlinx.coroutines.flow.Flow

interface TVShowsRepository {

    fun getTopRatedShows(): Flow<ResultRepository<List<ShowEntity>>>

}