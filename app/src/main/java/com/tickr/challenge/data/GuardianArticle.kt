package com.tickr.challenge.data

import com.google.gson.annotations.SerializedName

/**
 * Data class that represents an article from The Guardian.
 *
 * Not all of the fields returned from the API are represented here; only the ones used in this
 * project are listed below. For a full list of fields, consult the API documentation
 * [here](https://open-platform.theguardian.com/explore/).
 */
data class GuardianArticle(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("webUrl") val webUrl: String,
    @field:SerializedName("fields") val fields: ArticleFields,
)
