package com.example.nytimesapp.data.remote.reviews

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewsResponse(
    val status:String,
    val copyright:String,
    @SerialName("has_more")
    val hasMore:Boolean,
    @SerialName("num_results")
    val numResults:Int,
    @SerialName("results")
    val reviewsList:List<ReviewItemRemote>
)
