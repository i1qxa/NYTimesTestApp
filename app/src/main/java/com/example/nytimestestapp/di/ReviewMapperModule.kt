package com.example.nytimestestapp.di

import com.example.nytimestestapp.data.local.reviews.MapperReviewDB
import com.example.nytimestestapp.data.remote.reviews.MapperReviewRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ReviewMapperModule {

    @Singleton
    @Provides
    fun provideReviewRemoteMapper() = MapperReviewRemote()

    @Singleton
    @Provides
    fun provideReviewDBMapper() = MapperReviewDB()

}