package com.prismsoft.foody.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.prismsoft.foody.util.Constants

class RecipesViewModel(

): ViewModel() {

    fun applyQueries() = mapOf(
        Constants.QUERY_NUMBER to "50",
        Constants.QUERY_API_KEY to Constants.API_KEY,
        Constants.QUERY_TYPE to "snack",
        Constants.QUERY_DIET to "vegan",
        Constants.QUERY_ADD_RECIPE_INFORMATION to "true",
        Constants.QUERY_FILL_INGREDIENTS to "true"
    )
}