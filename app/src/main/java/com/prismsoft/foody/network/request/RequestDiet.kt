package com.prismsoft.foody.network.request

/**
 * Values for query-param "diet"
 */
sealed class RequestDiet(val value: String) {
    object GlutenFree: RequestDiet("Gluten Free")
    object Keto: RequestDiet("Ketogenic")
    object Vegetarian: RequestDiet("Vegetarian")
    object Vegan: RequestDiet("Vegan")
    object Pescetarian: RequestDiet("Pescetarian")
    object Primal: RequestDiet("Primal")
    object LowFodmap: RequestDiet("Low FODMAP")
    object Whole30: RequestDiet("Whole30")
}