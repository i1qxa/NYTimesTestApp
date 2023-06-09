package com.example.nytimestestapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nytimesapp.data.local.reviews.ReviewItemDB
import com.example.nytimesapp.data.local.reviews.ReviewsDao

@Database(
    version = 1,
    entities = [
        ReviewItemDB::class,
    ],
    exportSchema = false
)
abstract class AppDatabase:RoomDatabase() {

    abstract fun reviewsDao():ReviewsDao

}