package com.prismsoft.foody

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.prismsoft.foody.data.NetworkResult
import com.prismsoft.foody.data.Repository
import com.prismsoft.foody.data.model.RecipeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: MainApp
) : AndroidViewModel(application) {

    private val _recipesResponse: MutableLiveData<NetworkResult<RecipeResponse>> = MutableLiveData()
    val recipesResponse: LiveData<NetworkResult<RecipeResponse>> = _recipesResponse

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch(Dispatchers.IO) {
        getRecipesSafeCall(queries)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        _recipesResponse.value = NetworkResult.Loading()
        if(hasInternetConnection()){
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

    private fun handleResponse(response: Response<RecipeResponse>): NetworkResult<RecipeResponse>? {
        return when {
            response.message().toString().contains("timeout") -> NetworkResult.Error("Timeout")
            response.code() == 402 -> NetworkResult.Error("API Key Limited.")
            response.body()?.results.isNullOrEmpty() -> NetworkResult.Error("Recipes Not Found")
            response.isSuccessful -> {
                val recipes = response.body()!!
                NetworkResult.Success(recipes)
            }
            else -> NetworkResult.Error(response.message())
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
}