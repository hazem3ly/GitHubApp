package com.itg.githubapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itg.githubapp.data.network.response.RepoDetails
import com.itg.githubapp.data.network.response.Repository
import com.itg.githubapp.data.network.response.SearchReaslt
import com.itg.githubapp.data.network.response.UserDetails
import retrofit2.Response
import java.io.IOException

class NetworkDataSourceImpl(
    private val apiService: ApiService
) : NetworkDataSource {
    private val _userRepos = MutableLiveData<Response<List<RepoDetails>>>()
    override val userRepos: LiveData<Response<List<RepoDetails>>>
        get() = _userRepos

    override suspend fun userRepos(userName: String, page: Int) {
        try {
            val repo = apiService.userRepos(userName,page).await()
            _userRepos.postValue(repo)
        } catch (e: IOException) {
            Log.e("Connectivity", "No internet connection.", e)
            _userRepos.postValue(null)
        }
    }


    private val _repoDetails = MutableLiveData<Response<RepoDetails>>()
    override val repoDetails: LiveData<Response<RepoDetails>>
        get() = _repoDetails

    override suspend fun repoDetails(userName: String, repoName: String) {
        try {
            val repo = apiService.repoDetails(userName, repoName).await()
            _repoDetails.postValue(repo)
        } catch (e: IOException) {
            Log.e("Connectivity", "No internet connection.", e)
            _repoDetails.postValue(null)
        }
    }

    private val _userDetails = MutableLiveData<Response<UserDetails>>()
    override val userDetails: LiveData<Response<UserDetails>>
        get() = _userDetails

    override suspend fun userDetails(userName: String) {
        try {
            val user = apiService.userDetails(userName).await()
            _userDetails.postValue(user)
        } catch (e: IOException) {
            Log.e("Connectivity", "No internet connection.", e)
            _userDetails.postValue(null)
        }
    }

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