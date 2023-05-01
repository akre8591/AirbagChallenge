package com.example.airbarchallenge.data

import com.example.airbarchallenge.data.db.ShowsDao
import com.example.airbarchallenge.data.remote.TvShowsApi
import com.example.airbarchallenge.di.IoDispatcher
import com.example.airbarchallenge.domain.repositories.TVShowsRepository
import com.example.airbarchallenge.utils.ResultNetwork
import com.example.airbarchallenge.utils.ResultRepository
import com.example.airbarchallenge.utils.repositoryFlow
import com.example.airbarchallenge.utils.toEntity
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


class TVShowsRepositoryImp @Inject constructor(
    private val api: TvShowsApi,
    private val showsDao: ShowsDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : TVShowsRepository {

    override fun getTopRatedShows() = repositoryFlow(dispatcher) {
        when (val result = api.getTopRatedShows()) {
            is ResultNetwork.Success -> {
                val showList = result.data.results?.map {
                    it.toEntity()
                }
                showsDao.insertAllShows(showList.orEmpty())
                val localShowsList = showsDao.getAllShows()
                emit(ResultRepository.Success(localShowsList))
            }

            is ResultNetwork.Error -> {
                val localShowsList = showsDao.getAllShows()
                emit(
                    ResultRepository.Error(
                        Exception(result.error.exception),
                        showEntity = localShowsList
                    )
                )
            }

            is ResultNetwork.FatalError -> {
                val localShowsList = showsDao.getAllShows()
                emit(ResultRepository.Error(Exception(result.error), showEntity = localShowsList))
            }
        }
    }
}