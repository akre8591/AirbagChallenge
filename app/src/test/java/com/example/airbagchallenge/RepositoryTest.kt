package com.example.airbagchallenge

import com.example.airbarchallenge.data.TVShowsRepositoryImp
import com.example.airbarchallenge.data.db.ShowEntity
import com.example.airbarchallenge.data.db.ShowsDao
import com.example.airbarchallenge.data.remote.TvShowsApi
import com.example.airbarchallenge.domain.models.Result
import com.example.airbarchallenge.domain.models.TvShow
import com.example.airbarchallenge.utils.ResultNetwork
import com.example.airbarchallenge.utils.ResultRepository
import com.example.airbarchallenge.utils.TMDBError
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class RepositoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var tvShowsRepository: TVShowsRepositoryImp
    private val apiMock = mockk<TvShowsApi>(relaxed = true)
    private val daoMock = mockk<ShowsDao>(relaxed = true)
    private var testCoroutineDispatchers: CoroutineDispatcher = UnconfinedTestDispatcher()
    private val resultResponse =
        Result(
            listOf(
                TvShow(0, "test", 8.2, "fake_path", "fake_overview"),
                TvShow(1, "test", 9.2, "fake_path", "fake_overview")
            )
        )
    private val localTvShowList =
        listOf(
            ShowEntity(0, "test", 8.2, "fake_path", "fake_overview"),
            ShowEntity(1, "test", 9.2, "fake_path", "fake_overview")
        )

    @Before
    fun setup() {
        tvShowsRepository = TVShowsRepositoryImp(apiMock, daoMock, testCoroutineDispatchers)
    }


    @Test
    fun getTopRatedShowSuccess() = runTest {

        val tvShowItemsExpected = 2

        coEvery {
            apiMock.getTopRatedShows()
        } returns ResultNetwork.Success(resultResponse)

        coEvery {
            daoMock.insertAllShows(any())
        } returns Unit

        coEvery {
            daoMock.getAllShows()
        } returns localTvShowList

        tvShowsRepository.getTopRatedShows().collectLatest { result ->
            when (result) {
                is ResultRepository.Success -> {
                    assertEquals(tvShowItemsExpected, result.data.size)
                }

                is ResultRepository.Progress -> {
                    assertEquals(true, result.isLoading)
                }

                is ResultRepository.Error -> {
                    // invalid scenario
                }
            }
        }
    }

    @Test
    fun getTopRatedShowErrorNoLocalItems() = runTest {

        val tvShowItemsExpected = 0

        coEvery {
            apiMock.getTopRatedShows()
        } returns ResultNetwork.Error(TMDBError(404, "Error", Exception()))


        coEvery {
            daoMock.getAllShows()
        } returns emptyList()

        tvShowsRepository.getTopRatedShows().collectLatest { result ->
            when (result) {
                is ResultRepository.Success -> {
                    // invalid scenario
                }

                is ResultRepository.Progress -> {
                    assertEquals(true, result.isLoading)
                }

                is ResultRepository.Error -> {
                    assertEquals(tvShowItemsExpected, result.showEntity?.size)
                }
            }
        }
    }

    @Test
    fun getTopRatedShowErrorWithLocalItems() = runTest {

        val tvShowItemsExpected = 2

        coEvery {
            apiMock.getTopRatedShows()
        } returns ResultNetwork.Error(TMDBError(404, "Error", Exception()))


        coEvery {
            daoMock.getAllShows()
        } returns localTvShowList

        tvShowsRepository.getTopRatedShows().collectLatest { result ->
            when (result) {
                is ResultRepository.Success -> {
                    // invalid scenario
                }

                is ResultRepository.Progress -> {
                    assertEquals(true, result.isLoading)
                }

                is ResultRepository.Error -> {
                    assertEquals(tvShowItemsExpected, result.showEntity?.size)
                }
            }
        }
    }

    @Test
    fun getTopRatedShowFatalErrorNoLocalItems() = runTest {

        val tvShowItemsExpected = 0

        coEvery {
            apiMock.getTopRatedShows()
        } returns ResultNetwork.FatalError(Exception())


        coEvery {
            daoMock.getAllShows()
        } returns emptyList()

        tvShowsRepository.getTopRatedShows().collectLatest { result ->
            when (result) {
                is ResultRepository.Success -> {
                    // invalid scenario
                }

                is ResultRepository.Progress -> {
                    assertEquals(true, result.isLoading)
                }

                is ResultRepository.Error -> {
                    assertEquals(tvShowItemsExpected, result.showEntity?.size)
                }
            }
        }
    }

    @Test
    fun getTopRatedShowFatalErrorWithLocalItems() = runTest {

        val tvShowItemsExpected = 2

        coEvery {
            apiMock.getTopRatedShows()
        } returns ResultNetwork.FatalError(Exception())


        coEvery {
            daoMock.getAllShows()
        } returns localTvShowList

        tvShowsRepository.getTopRatedShows().collectLatest { result ->
            when (result) {
                is ResultRepository.Success -> {
                    // invalid scenario
                }

                is ResultRepository.Progress -> {
                    assertEquals(true, result.isLoading)
                }

                is ResultRepository.Error -> {
                    assertEquals(tvShowItemsExpected, result.showEntity?.size)
                }
            }
        }
    }


}