package com.example.nytimesapp.data.remote.reviews

import kotlinx.serialization.Serializable

@Serializable
data class MultimediaReviewRemote(
    val type:String,
    val src:String,
    val height:Int,
    val width:Int,
)
