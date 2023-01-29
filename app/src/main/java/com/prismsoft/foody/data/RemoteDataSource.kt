package com.prismsoft.foody.data

import android.util.Log
import com.prismsoft.foody.network.FoodRecipesApi
import com.prismsoft.foody.data.model.RecipeResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {
    suspend fun getRecipes(queries: Map<String, String>): Response<RecipeResponse> {
        Log.i("JAMES::", "getRecipes: Getting Recipes $queries")
        return foodRecipesApi.getRecipes(queries)
    }
}