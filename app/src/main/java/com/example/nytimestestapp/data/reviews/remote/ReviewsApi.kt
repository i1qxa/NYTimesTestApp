package com.example.nytimesapp.data.remote.reviews

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ReviewsApi {
    @GET("reviews/search.json?")
    suspend fun getAllReviews(
        @Query("offset") offset:Int?,
        @Query("publication-date") dateRange:String?,
        @Query("query") query:String?,
        @Query("api-key") appKey:String = "ua0FkUM6o203AZt9iYPIrah9Wos0a4Yf",
    ): Response<ReviewsResponse>
}