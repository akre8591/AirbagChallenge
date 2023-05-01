package com.example.airbagchallenge.viewmodel


import com.example.airbagchallenge.utils.MainDispatcherRule
import com.example.airbarchallenge.data.db.ShowEntity
import com.example.airbarchallenge.domain.repositories.TVShowsRepository
import com.example.airbarchallenge.presentation.ListRatedTvShowsState
import com.example.airbarchallenge.presentation.MainViewModel
import com.example.airbarchallenge.presentation.SingleShowState
import com.example.airbarchallenge.utils.ResultRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var mainViewModel: MainViewModel
    private val repository = mockk<TVShowsRepository>(relaxed = true)
    private val localTvShowList =
        listOf(
            ShowEntity(0, "test", 8.2, "fake_path", "fake_overview"),
            ShowEntity(1, "test", 9.2, "fake_path", "fake_overview")
        )
    private val localEntity = ShowEntity(0, "test", 8.2, "fake_path", "fake_overview")

    @Before
    fun setup() {
        mainViewModel = MainViewModel(repository)
    }


    @Test
    fun getTopRatedShowListSuccess() = runTest {

        coEvery {
            repository.getTopRatedShows()
        } returns flow {
            emit(ResultRepository.Success(localTvShowList))
        }

        mainViewModel.getTopRatedShowList()

        val state = mainViewModel.topRatedShowList.value

        assert(state is ListRatedTvShowsState.Success)

    }

    @Test
    fun getTopRatedShowListProgress() = runTest {

        coEvery {
            repository.getTopRatedShows()
        } returns flow {
            emit(ResultRepository.Progress(true))
        }

        mainViewModel.getTopRatedShowList()

        val state = mainViewModel.topRatedShowList.value

        assert(state is ListRatedTvShowsState.Loading)

    }

    @Test
    fun getTopRatedShowListError() = runTest {

        coEvery {
            repository.getTopRatedShows()
        } returns flow {
            emit(ResultRepository.Error(Exception()))
        }

        mainViewModel.getTopRatedShowList()

        val state = mainViewModel.topRatedShowList.value

        assert(state is ListRatedTvShowsState.Error)

    }

    @Test
    fun getShowByIdSuccess() = runTest {

        coEvery {
            repository.getShowById(any())
        } returns flow {
            emit(ResultRepository.Success(localEntity))
        }

        mainViewModel.getShowById(1)

        val state = mainViewModel.showEntity.value

        assert(state is SingleShowState.Success)

    }

    @Test
    fun getShowByIdProgress() = runTest {

        coEvery {
            repository.getShowById(any())
        } returns flow {
            emit(ResultRepository.Progress(true))
        }

        mainViewModel.getShowById(1)

        val state = mainViewModel.showEntity.value

        assert(state is SingleShowState.Loading)

    }

    @Test
    fun getShowByIdError() = runTest {

        coEvery {
            repository.getShowById(any())
        } returns flow {
            emit(ResultRepository.Error(Exception()))
        }

        mainViewModel.getShowById(1)

        val state = mainViewModel.showEntity.value

        assert(state is SingleShowState.Error)

    }


}