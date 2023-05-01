package com.example.airbarchallenge.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ShowEntity::class], version = 1, exportSchema = false)
abstract class TvShowsDataBase : RoomDatabase() {

    abstract fun showsDao(): ShowsDao

}