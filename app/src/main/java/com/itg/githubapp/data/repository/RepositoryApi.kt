package com.itg.githubapp.data.repository

import androidx.lifecycle.LiveData
import com.itg.githubapp.data.network.response.RepoDetails
import com.itg.githubapp.data.network.response.Repository
import com.itg.githubapp.data.network.response.SearchReaslt
import com.itg.githubapp.data.network.response.UserDetails
import retrofit2.Response

interface RepositoryApi {
    suspend fun getRepos(since: Int): LiveData<Response<List<Repository>>>
    suspend fun searchRepos(query: String, page: Int, perPage: Int): LiveData<Response<SearchReaslt>>
    suspend fun getRepoDetails(userName: String, repoName: String): LiveData<Response<RepoDetails>>
    suspend fun getUserDetails(userName: String): LiveData<Response<UserDetails>>
    suspend fun getUserRepos(userName: String, page: Int): LiveData<Response<List<RepoDetails>>>
}