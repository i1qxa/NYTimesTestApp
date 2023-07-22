package com.example.nytimestestapp.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.nytimestestapp.data.local.reviews.ReviewItemDB
import com.example.nytimestestapp.data.local.reviews.ReviewsDao
import com.example.nytimestestapp.data.remote.reviews.MapperReviewRemote
import com.example.nytimestestapp.data.remote.reviews.ReviewItemRemote
import com.example.nytimestestapp.data.remote.reviews.ReviewsApi
import com.example.nytimestestapp.data.remote.reviews.ReviewsResponse
import com.example.nytimestestapp.domain.reviews.ReviewQueryParams
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import retrofit2.Response
import java.util.Calendar

@ExperimentalPagingApi
class ReviewsRemoteMediator @AssistedInject constructor (
    private val reviewsDao: ReviewsDao,
    private val reviewApi: ReviewsApi,
    private val mapper: MapperReviewRemote,
    @Assisted private val reviewQueryParams: ReviewQueryParams?
) : RemoteMediator<Int, ReviewItemDB>() {
    private var pageIndex = 0
    private var lastRequestTimeInMils:Long = 0
    private var currentRequestTimeInMils:Long = 0
    private var calendar = Calendar.getInstance()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ReviewItemDB>
    ): MediatorResult {

        pageIndex = getPageIndex(loadType) ?: return MediatorResult.Success(
            endOfPaginationReached = true
        )
        Log.i("PAGE_INDEX = ", pageIndex.toString())
        val limit = state.config.pageSize
        val offset = pageIndex * limit

        return try {
            val reviews = fetchReviews(offset)
            if (loadType == LoadType.REFRESH) {
                reviewsDao.updateReviewsData(reviews)
            } else {
                reviewsDao.insertListReviews(reviews)
            }
            MediatorResult.Success(
                endOfPaginationReached = reviews.size < limit
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
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

//    private suspend fun fetchReviews(
//        offset: Int
//        ): List<ReviewItemDB> {
//        val listReviewRemote = reviewApi.getAllReviews(
//            offset = offset,
//            dateRange = reviewQueryParams?.dateRange,
//            query = reviewQueryParams?.query
//        ).body()?.reviewsList ?: emptyList()
//        return mapper.mapListReviewRemoteToListDB(listReviewRemote)
//    }

    private suspend fun fetchReviews(
        offset: Int
    ): List<ReviewItemDB> {
        var step = 0
        var isSuccess = false
        var listReviewRemote = emptyList<ReviewItemRemote>()
        while (!isSuccess){
            Log.d("OFFSET_NEW", offset.toString())
            currentRequestTimeInMils = calendar.timeInMillis
            if (currentRequestTimeInMils - lastRequestTimeInMils < 3000){
                delay(3000)
                lastRequestTimeInMils = currentRequestTimeInMils
            }
            val response = reviewApi.getAllReviews(
                offset = offset,
                dateRange = reviewQueryParams?.dateRange,
                query = reviewQueryParams?.query
            )
            if (response.isSuccessful){
                listReviewRemote = response.body()?.reviewsList?: emptyList()
                isSuccess = true
            }
            else{
                step++
            }
            if (step>5) isSuccess = true
        }

//            .body()?.reviewsList ?: emptyList()
        return mapper.mapListReviewRemoteToListDB(listReviewRemote)
    }

    @AssistedFactory
    interface Factory {
        fun create(reviewQueryParams: ReviewQueryParams?): ReviewsRemoteMediator
    }
}