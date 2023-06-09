package com.example.nytimestestapp.data.local.reviews

import com.example.nytimestestapp.domain.reviews.ReviewItem
import javax.inject.Singleton

@Singleton
class MapperReviewDB {

    fun mapReviewDBToReviewItem(itemDB: ReviewItemDB) = ReviewItem(
        id = itemDB.id,
        mpaaRating = itemDB.mpaaRating,
        criticPick = itemDB.criticsPick,
        byline = itemDB.byline,
        headLine = itemDB.headline,
        summaryShort = itemDB.summaryShort,
        publicationDate = itemDB.publicationDate,
        openingDate = itemDB.opening_date,
        dateUpdated = itemDB.dateUpdated,
        reviewItemLink = itemDB.link,
        reviewItemImg = itemDB.imgSrcLocal
    )

    fun mapListReviewDBToListReviewItem(listDB:List<ReviewItemDB>) = listDB.map { reviewItemDB ->
        mapReviewDBToReviewItem(reviewItemDB)
    }

}