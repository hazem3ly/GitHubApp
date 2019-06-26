package com.itg.githubapp.data.repository

import androidx.lifecycle.LiveData
import com.itg.githubapp.data.network.NetworkDataSource
import com.itg.githubapp.data.network.response.Repository
import com.itg.githubapp.data.network.response.SearchReaslt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class RepositoryApiImpl(
    private val networkDataSource: NetworkDataSource
) : RepositoryApi {
    override suspend fun searchRepos(query: String, page: Int, perPage: Int): LiveData<Response<SearchReaslt>> {
        return withContext(Dispatchers.IO) {
            networkDataSource.searchRepositories(query, page, perPage)
            return@withContext networkDataSource.searchRepositories
        }
    }

    override suspend fun getRepos(since: Int): LiveData<Response<List<Repository>>> {
        return withContext(Dispatchers.IO) {
            networkDataSource.getRepositories(since)
            return@withContext networkDataSource.repositories
        }
    }
}