package com.example.nytimestestapp.di

import androidx.paging.ExperimentalPagingApi
import com.example.nytimestestapp.data.reviews.ReviewRepositoryImpl
import com.example.nytimestestapp.domain.reviews.ReviewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
interface ReviewRepositoryModule {
    @Binds
    fun bindReviewRepository(
        reviewRepository: ReviewRepositoryImpl
    ): ReviewsRepository
}