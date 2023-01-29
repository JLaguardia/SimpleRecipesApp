package com.prismsoft.foody

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prismsoft.foody.data.NetworkResult
import com.prismsoft.foody.data.Repository
import com.prismsoft.foody.data.model.RecipeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _recipesResponse = MutableStateFlow<NetworkResult<RecipeResponse>>(NetworkResult.Loading())
    val recipesResponse: StateFlow<NetworkResult<RecipeResponse>> = _recipesResponse

    fun getRecipes(queries: Map<String, String> = bootstrapQuery, context: Context) = viewModelScope.launch(Dispatchers.IO) {
        getRecipesSafeCall(queries, context)
    }

    private val bootstrapQuery = mapOf(
        "diet" to "vegan",
        "instructionsRequired" to "true",
        "addRecipeInformation" to "true",
        "number" to "15"
    )

    private suspend fun getRecipesSafeCall(queries: Map<String, String>, context: Context) {
        _recipesResponse.value = NetworkResult.Loading()
        if(hasInternetConnection(context)){
            try {
                val response = repository.remote.getRecipes(queries)
                _recipesResponse.value = handleResponse(response)
            } catch (e: Exception){
                _recipesResponse.value = NetworkResult.Error(e.message)
            }
        } else {
            _recipesResponse.value = NetworkResult.Error(message = "No Internet Connection.")
        }
    }

    private fun handleResponse(response: Response<RecipeResponse>): NetworkResult<RecipeResponse> {
        Log.w("JAMES::", "response code: ${response.code()} | ${response.message()}")
        return when {
            response.message().toString().contains("timeout") -> NetworkResult.Error("Timeout")
            response.code() == 402 -> NetworkResult.Error("API Key Limited.")
            response.body()?.results.isNullOrEmpty() -> NetworkResult.Error("Recipes Not Found")
            response.isSuccessful -> {
                val recipes = response.body()!!
                Log.w("JAMES::", "handleResponse: success: ${recipes.results.size} items")
                NetworkResult.Success(recipes)
            }
            else -> NetworkResult.Error(response.message())
        }
    }

    private fun hasInternetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
}