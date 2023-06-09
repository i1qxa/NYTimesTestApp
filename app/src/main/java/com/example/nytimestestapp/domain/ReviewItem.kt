package com.example.nytimesapp.domain.reviews

data class ReviewItem(
    val id:Long,
    val mpaaRating:String?,
    val criticPick:Int?,
    val byline:String?,
    val headLine:String?,
    val summaryShort:String?,
    val publicationDate:String?,
    val openingDate:String?,
    val dateUpdated:String?,
    val reviewItemLink: String?,
    val reviewItemImg:String?,
)
