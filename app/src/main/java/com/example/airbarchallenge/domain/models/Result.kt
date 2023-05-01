package com.example.airbarchallenge.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "results")
    val results: List<TvShow>?
)

@JsonClass(generateAdapter = true)
data class TvShow(

    @Json(name = "id")
    val id: Int?,

    @Json(name = "name")
    val name: String?,

    @Json(name = "vote_average")
    val voteAverage: Double?,

    @Json(name = "poster_path")
    val posterPath: String?,

    @Json(name = "overview")
    val overview: String?
)
