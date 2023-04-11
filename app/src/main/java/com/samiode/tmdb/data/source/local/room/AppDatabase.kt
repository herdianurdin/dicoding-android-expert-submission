package com.samiode.tmdb.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.samiode.tmdb.data.source.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}