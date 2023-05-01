package com.example.airbarchallenge.utils

import com.example.airbarchallenge.data.db.ShowEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

sealed class ResultRepository<out T> {
    data class Progress(val isLoading: Boolean) : ResultRepository<Nothing>()
    data class Success<out T>(val data: T) : ResultRepository<T>()
    data class Error(
        val error: Exception,
        val httpErrorCode: Int? = null,
        val message: String? = null,
        val showEntity: List<ShowEntity>? = null
    ) : ResultRepository<Nothing>()

}

fun <T> repositoryFlow(
    dispatcher: CoroutineDispatcher,
    block: suspend FlowCollector<ResultRepository<T>>.() -> Unit
) = flow {
    block(this)
}.onStart {
    emit(ResultRepository.Progress(isLoading = true))
}.catch {
    emit(ResultRepository.Error(Exception(it)))
}.flowOn(dispatcher)