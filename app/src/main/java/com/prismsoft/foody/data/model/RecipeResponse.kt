package com.prismsoft.foody.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecipeResponse(
    @Json(name = "results")
    val results: List<Result>,
)