package com.example.nytimestestapp.data.reviews

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.nytimestestapp.data.ReviewsRemoteMediator
import com.example.nytimestestapp.data.local.reviews.MapperReviewDB
import com.example.nytimestestapp.data.local.reviews.ReviewsDao
import com.example.nytimestestapp.domain.reviews.ReviewItem
import com.example.nytimestestapp.domain.reviews.ReviewQueryParams
import com.example.nytimestestapp.domain.reviews.ReviewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalPagingApi
@Singleton
class ReviewRepositoryImpl @Inject constructor(
    private val reviewsDao: ReviewsDao,
    private val remoteMediatorFactory: ReviewsRemoteMediator.Factory,
    private val mapper: MapperReviewDB
) : ReviewsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getReviewsList(reviewQueryParams: ReviewQueryParams?)
            : Flow<PagingData<ReviewItem>> {
        return Pager(config = PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = PAGE_SIZE,
        ),
            remoteMediator = remoteMediatorFactory.create(reviewQueryParams),
            pagingSourceFactory = { reviewsDao.getAllReviews() }
        ).flow
            .map { pagingData ->
                pagingData.map { reviewItemDB ->
                    mapper.mapReviewDBToReviewItem(reviewItemDB)
                }
            }
    }

    private companion object {
        const val PAGE_SIZE = 20
    }
}