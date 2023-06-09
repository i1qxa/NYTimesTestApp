package com.example.nytimestestapp.data.reviews.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinkRemote(
    val type:String,
    val url:String,
    @SerialName("suggested_link_text")
    val suggestedLinkText:String,
)
