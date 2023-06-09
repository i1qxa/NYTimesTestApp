package com.example.nytimestestapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.nytimestestapp.data.local.reviews.ReviewItemDB
import com.example.nytimestestapp.data.local.reviews.ReviewsDao
import com.example.nytimestestapp.data.remote.reviews.MapperReviewRemote
import com.example.nytimestestapp.data.remote.reviews.ReviewsApi
import com.example.nytimestestapp.domain.reviews.ReviewQueryParams
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@ExperimentalPagingApi
class ReviewsRemoteMediator @AssistedInject constructor (
    private val reviewsDao: ReviewsDao,
    private val reviewApi: ReviewsApi,
    private val mapper: MapperReviewRemote,
    @Assisted private val reviewQueryParams: ReviewQueryParams?
) : RemoteMediator<Int, ReviewItemDB>() {
    private var pageIndex = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ReviewItemDB>
    ): MediatorResult {

        pageIndex = getPageIndex(loadType) ?: return RemoteMediator.MediatorResult.Success(
            endOfPaginationReached = true
        )

        val limit = state.config.pageSize
        val offset = pageIndex * limit

        return try {
            val reviews = fetchReviews(offset)
            if (loadType == LoadType.REFRESH) {
                reviewsDao.updateReviewsData(reviews)
            } else {
                reviewsDao.insertListReviews(reviews)
            }
            RemoteMediator.MediatorResult.Success(
                endOfPaginationReached = reviews.size < limit
            )
        } catch (e: Exception) {
            RemoteMediator.MediatorResult.Error(e)
        }
    }

    private fun getPageIndex(loadType: LoadType): Int? {
        pageIndex = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return null
            LoadType.APPEND -> ++pageIndex
        }
        return pageIndex
    }

    private suspend fun fetchReviews(
        offset: Int
        ): List<ReviewItemDB> {
        val listReviewRemote = reviewApi.getAllReviews(
            offset = offset,
            dateRange = reviewQueryParams?.dateRange,
            query = reviewQueryParams?.query
        ).body()?.reviewsList ?: emptyList()
        return mapper.mapListReviewRemoteToListDB(listReviewRemote)
    }

    @AssistedFactory
    interface Factory {
        fun create(reviewQueryParams: ReviewQueryParams?): ReviewsRemoteMediator
    }
}