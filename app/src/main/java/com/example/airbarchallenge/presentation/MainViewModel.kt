package com.example.airbarchallenge.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airbarchallenge.domain.models.TvShow
import com.example.airbarchallenge.domain.repositories.TVShowsRepository
import com.example.airbarchallenge.utils.ResultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TVShowsRepository
) : ViewModel() {

    private val _topRatedShowList: MutableState<ListRatedTvShowsState> =
        mutableStateOf(ListRatedTvShowsState.Loading)
    val topRatedShowList: State<ListRatedTvShowsState> = _topRatedShowList

    fun getTopRatedShowList() = viewModelScope.launch {
        repository.getTopRatedShows().collectLatest { result ->
            when (result) {
                is ResultRepository.Success -> {
                    _topRatedShowList.value = ListRatedTvShowsState.Success(result.data.results)
                }

                is ResultRepository.Progress -> {
                    _topRatedShowList.value = ListRatedTvShowsState.Loading
                }

                is ResultRepository.Error -> {
                    _topRatedShowList.value = ListRatedTvShowsState.Error
                }
            }
        }
    }
}

sealed class ListRatedTvShowsState {
    object Loading : ListRatedTvShowsState()
    data class Success(val showList: List<TvShow>?) : ListRatedTvShowsState()
    object Error : ListRatedTvShowsState()
}