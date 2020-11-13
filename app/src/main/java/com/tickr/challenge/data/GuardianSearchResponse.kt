package com.tickr.challenge.data

import com.google.gson.annotations.SerializedName

/**
 * Data class that represents a content search response from The Guardian API.
 *
 * Not all of the fields returned from the API are represented here; only the ones used in this
 * project are listed below. For a full list of fields, consult the API documentation
 * [here](https://open-platform.theguardian.com/explore/).
 */
data class GuardianSearchResponse(
    @field:SerializedName("response") val root: GuardianSearchResponseRoot,
)
