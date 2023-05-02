package com.example.airbarchallenge.domain.repositories

import com.example.airbarchallenge.data.db.ShowEntity
import com.example.airbarchallenge.utils.ResultRepository
import kotlinx.coroutines.flow.Flow

interface TVShowsRepository {

    fun getTopRatedShows(): Flow<ResultRepository<List<ShowEntity>>>

    fun getShowById(id: Int): Flow<ResultRepository<ShowEntity>>

}