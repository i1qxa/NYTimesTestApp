package com.example.nytimesapp.domain.reviews

data class ReviewItemImg(
    val id:Long,
    val type:String,
    val srcRemote:String,
    val srcLocal:String,
    val height:Int,
    val width:Int,
)
