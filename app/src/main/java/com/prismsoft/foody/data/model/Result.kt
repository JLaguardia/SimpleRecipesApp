package com.prismsoft.foody.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "aggregateLikes")
    val aggregateLikes: Int,
    @Json(name = "cookingMinutes")
    val cookingMinutes: Int,
    @Json(name = "dairyFree")
    val dairyFree: Boolean,
    @Json(name = "glutenFree")
    val glutenFree: Boolean,
    @Json(name = "image")
    val image: String,
    @Json(name = "lowFodmap")
    val lowFodmap: Boolean,
    @Json(name = "preparationMinutes")
    val preparationMinutes: Int,
    @Json(name = "readyInMinutes")
    val readyInMinutes: Int,
    @Json(name = "servings")
    val servings: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "summary")
    val summary: String,
    @Json(name = "vegan")
    val vegan: Boolean,
    @Json(name = "vegetarian")
    val vegetarian: Boolean,
){
    fun toListItem() {

    }
}