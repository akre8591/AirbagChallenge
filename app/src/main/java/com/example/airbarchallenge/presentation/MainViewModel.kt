package com.example.airbarchallenge.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airbarchallenge.data.db.ShowEntity
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

    private val _showEntity: MutableState<SingleShowState> = mutableStateOf(SingleShowState.Loading)
    val showEntity: State<SingleShowState> = _showEntity

    fun getTopRatedShowList() = viewModelScope.launch {
        repository.getTopRatedShows().collectLatest { result ->
            when (result) {
                is ResultRepository.Success -> {
                    _topRatedShowList.value = ListRatedTvShowsState.Success(result.data)
                }

                is ResultRepository.Progress -> {
                    _topRatedShowList.value = ListRatedTvShowsState.Loading
                }

                is ResultRepository.Error -> {
                    _topRatedShowList.value = ListRatedTvShowsState.Error(result.showEntity)
                }
            }
        }
    }

    fun getShowById(id: Int) = viewModelScope.launch {
        repository.getShowById(id).collectLatest { result ->
            when (result) {
                is ResultRepository.Success -> {
                    _showEntity.value = SingleShowState.Success(result.data)
                }

                is ResultRepository.Progress -> {
                    _showEntity.value = SingleShowState.Loading
                }

                is ResultRepository.Error -> {
                    _showEntity.value = SingleShowState.Error
                }
            }
        }
    }
}

sealed class ListRatedTvShowsState {
    object Loading : ListRatedTvShowsState()
    data class Success(val list: List<ShowEntity>?) : ListRatedTvShowsState()
    data class Error(val list: List<ShowEntity>? = null) : ListRatedTvShowsState()
}

sealed class SingleShowState {
    object Loading : SingleShowState()
    data class Success(val show: ShowEntity) : SingleShowState()
    object Error : SingleShowState()
}