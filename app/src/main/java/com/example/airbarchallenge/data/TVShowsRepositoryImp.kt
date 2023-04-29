package com.example.airbarchallenge.data

import com.example.airbarchallenge.data.remote.TvShowsApi
import com.example.airbarchallenge.domain.repositories.TVShowsRepository
import com.example.airbarchallenge.utils.ResultNetwork
import com.example.airbarchallenge.utils.ResultRepository
import com.example.airbarchallenge.utils.repositoryFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class TVShowsRepositoryImp @Inject constructor(
    private val api: TvShowsApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : TVShowsRepository {

    override fun getTopRatedShows() = repositoryFlow(dispatcher) {
        when (val result = api.getTopRatedShows()) {
            is ResultNetwork.Success -> {
                emit(ResultRepository.Success(result.data))
            }

            is ResultNetwork.Error -> {
                emit(ResultRepository.Error(Exception(result.error.exception)))
            }

            is ResultNetwork.FatalError -> {
                emit(ResultRepository.Error(Exception(result.error)))
            }
        }
    }
}