package com.example.airbarchallenge.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShowEntity(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val voteAverage: Double?,
    val posterPath: String?
)

