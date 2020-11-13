package com.tickr.challenge.data

import com.google.gson.annotations.SerializedName

/**
 * Data class that represents ArticleFields available for a GuardianArticle article.
 *
 * Although several article fields are available, this project uses only uses three of them.
 * For more details, consult the API documentation
 * [here](https://open-platform.theguardian.com/explore/).
 */
data class ArticleFields(
    @field:SerializedName("headline") val headline: String,
    @field:SerializedName("trailText") val trailText: String,
    @field:SerializedName("thumbnail") val thumbnail: String
)
