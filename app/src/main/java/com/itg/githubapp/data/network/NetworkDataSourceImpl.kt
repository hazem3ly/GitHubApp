package com.itg.githubapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itg.githubapp.data.network.response.Repository
import com.itg.githubapp.data.network.response.SearchReaslt
import retrofit2.Response
import java.io.IOException

class NetworkDataSourceImpl(
    private val apiService: ApiService
) : NetworkDataSource {
    private val _searchRepositoriesResponse = MutableLiveData<Response<SearchReaslt>>()
    override val searchRepositories: LiveData<Response<SearchReaslt>>
        get() = _searchRepositoriesResponse

    override suspend fun searchRepositories(query: String,page:Int,perPage:Int) {
        try {
            val repositories = apiService.searchRepositories(query,page, perPage).await()
            _searchRepositoriesResponse.postValue(repositories)
        } catch (e: IOException) {
            Log.e("Connectivity", "No internet connection.", e)
            _searchRepositoriesResponse.postValue(null)
        }
    }

    private val _repositoriesResponse = MutableLiveData<Response<List<Repository>>>()
    override val repositories: LiveData<Response<List<Repository>>>
        get() = _repositoriesResponse

    override suspend fun getRepositories(since: Int) {
        try {
            val repositories = apiService.repositories(since).await()
            _repositoriesResponse.postValue(repositories)
        } catch (e: IOException) {
            Log.e("Connectivity", "No internet connection.", e)
            _repositoriesResponse.postValue(null)
        }
    }

}