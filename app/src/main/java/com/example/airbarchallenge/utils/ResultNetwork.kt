package com.example.airbarchallenge.utils

import org.json.JSONObject
import retrofit2.Response

sealed class ResultNetwork<out T> {
    data class Success<out T>(val data: T) : ResultNetwork<T>()
    data class Error(val error: TMDBError) : ResultNetwork<Nothing>()
    data class FatalError(val error: Exception) : ResultNetwork<Nothing>()
}

suspend fun <T> getResult(callBlock: suspend () -> Response<T>): ResultNetwork<T> {
    return try {
        val response = callBlock()
        if (response.isSuccessful) {
            response.body()?.let { responseBody ->
                ResultNetwork.Success(responseBody)
            } ?: run {
                val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                ResultNetwork.Error(
                    TMDBError(
                        response.code(),
                        response.message(),
                        Exception(jsonObj.getString("error_description"))
                    )
                )
            }
        } else {
            val error = response.errorBody()?.charStream()?.readText()?.let {
                val jsonObj = JSONObject(it)
                jsonObj.getString("error_description")
            }
            ResultNetwork.Error(
                TMDBError(
                    response.code(), response.message(), Exception(error ?: "")
                )
            )
        }
    } catch (e: Exception) {
        ResultNetwork.FatalError(e)
    }
}

data class TMDBError(
    val httpErrorCode: Int,
    val message: String?,
    val exception: Exception?,
)