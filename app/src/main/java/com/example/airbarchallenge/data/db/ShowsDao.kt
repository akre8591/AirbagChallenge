package com.example.airbarchallenge.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ShowsDao {

    @Insert(onConflict = REPLACE)
    fun insertShow(entity: ShowEntity)

    @Transaction
    fun insertAllShows(showsList: List<ShowEntity>) {
        showsList.forEach {
            insertShow(it)
        }
    }

    @Query("SELECT * FROM ShowEntity")
    fun getAllShows(): List<ShowEntity>

}