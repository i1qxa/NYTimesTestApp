package com.example.nytimestestapp.di

import android.content.Context
import androidx.room.Room
import com.example.nytimestestapp.data.AppDatabase
import com.example.nytimestestapp.data.local.reviews.ReviewsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DB_NAME
        )
            .build()
    }

    @Provides
    fun provideReviewsDao(database: AppDatabase): ReviewsDao {
        return database.reviewsDao()
    }

    private companion object {
        const val DB_NAME = "NYTimesDB"
    }
}