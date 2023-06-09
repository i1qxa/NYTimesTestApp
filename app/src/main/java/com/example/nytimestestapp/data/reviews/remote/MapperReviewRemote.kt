package com.example.nytimesapp.data.remote.reviews

import com.example.nytimesapp.data.local.reviews.ReviewItemDB

class MapperReviewRemote {

    fun mapReviewRemoteToDB(itemRemote: ReviewItemRemote) = ReviewItemDB(
        id = 0,
        displayTitle = itemRemote.displayTitle,
        mpaaRating = itemRemote.mpaaRating,
        criticsPick = itemRemote.criticsPick,
        byline = itemRemote.byline,
        headline = itemRemote.headline,
        summaryShort = itemRemote.summaryShort,
        publicationDate = itemRemote.publicationDate,
        opening_date = itemRemote.opening_date?:"",
        dateUpdated = itemRemote.dateUpdated,
        link = itemRemote.link.url,
        imgSrcLocal = itemRemote.multimedia.src
    )

    fun mapListReviewRemoteToListDB(listRemote:List<ReviewItemRemote>) = listRemote.map { reviewItemRemote ->
        mapReviewRemoteToDB(reviewItemRemote)
    }

}