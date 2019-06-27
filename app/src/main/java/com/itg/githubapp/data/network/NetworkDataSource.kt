package com.itg.githubapp.data.network

import androidx.lifecycle.LiveData
import com.itg.githubapp.data.network.response.RepoDetails
import com.itg.githubapp.data.network.response.Repository
import com.itg.githubapp.data.network.response.SearchReaslt
import com.itg.githubapp.data.network.response.UserDetails
import retrofit2.Response

interface NetworkDataSource {
    val repositories: LiveData<Response<List<Repository>>>
    suspend fun getRepositories(since: Int)

    val searchRepositories: LiveData<Response<SearchReaslt>>
    suspend fun searchRepositories(query: String, page: Int, perPage: Int)

    val repoDetails: LiveData<Response<RepoDetails>>
    suspend fun repoDetails(userName: String, repoName: String)

    val userDetails: LiveData<Response<UserDetails>>
    suspend fun userDetails(userName: String)

    val userRepos: LiveData<Response<List<RepoDetails>>>
    suspend fun userRepos(userName: String, page: Int)
}