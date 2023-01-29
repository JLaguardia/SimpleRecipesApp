package com.prismsoft.foody.network.request

/**
 * Values for query-param "intolerances" (mult csv)
 */
sealed class RequestIntolerance(val value: String) {
    object Dairy: RequestIntolerance("Dairy")
    object Egg: RequestIntolerance("Egg")
    object Gluten: RequestIntolerance("Gluten")
    object Grain: RequestIntolerance("Grain")
    object Peanut: RequestIntolerance("Peanut")
    object Seafood: RequestIntolerance("Seafood")
    object Sesame: RequestIntolerance("Sesame")
    object Shellfish: RequestIntolerance("Shellfish")
    object Soy: RequestIntolerance("Soy")
    object Sulfite: RequestIntolerance("Sulfite")
    object TreeNut: RequestIntolerance("Tree Nut")
    object Wheat: RequestIntolerance("Wheat")
}