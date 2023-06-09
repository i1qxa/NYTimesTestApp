package com.example.nytimesapp.data.remote.reviews

import com.example.nytimestestapp.data.reviews.remote.LinkRemote
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewItemRemote(
    @SerialName("display_title")
    val displayTitle:String,
    @SerialName("mpaa_rating")
    val mpaaRating:String,
    @SerialName("critics_pick")
    val criticsPick:Int,
    val byline:String,
    val headline:String,
    @SerialName("summary_short")
    val summaryShort:String,
    @SerialName("publication_date")
    val publicationDate: String,
    @SerialName("opening_date")
    val opening_date:String? = "",
    @SerialName("date_updated")
    val dateUpdated:String,
    val link: LinkRemote,
    val multimedia:MultimediaReviewRemote,
)
