package com.itg.githubapp.data.network

import androidx.lifecycle.LiveData
import com.itg.githubapp.data.network.response.Repository
import com.itg.githubapp.data.network.response.SearchReaslt
import retrofit2.Response

interface NetworkDataSource {
    val repositories: LiveData<Response<List<Repository>>>
    suspend fun getRepositories(since: Int)

    val searchRepositories: LiveData<Response<SearchReaslt>>
    suspend fun searchRepositories(query: String,page:Int,perPage:Int)
}