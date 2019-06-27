package com.itg.githubapp.data.repository

import androidx.lifecycle.LiveData
import com.itg.githubapp.data.network.NetworkDataSource
import com.itg.githubapp.data.network.response.RepoDetails
import com.itg.githubapp.data.network.response.Repository
import com.itg.githubapp.data.network.response.SearchReaslt
import com.itg.githubapp.data.network.response.UserDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class RepositoryApiImpl(
    private val networkDataSource: NetworkDataSource
) : RepositoryApi {
    override suspend fun getUserRepos(userName: String, page: Int): LiveData<Response<List<RepoDetails>>> {
        return withContext(Dispatchers.IO) {
            networkDataSource.userRepos(userName, page)
            return@withContext networkDataSource.userRepos
        }
    }

    override suspend fun getRepoDetails(userName: String, repoName: String): LiveData<Response<RepoDetails>> {
        return withContext(Dispatchers.IO) {
            networkDataSource.repoDetails(userName, repoName)
            return@withContext networkDataSource.repoDetails
        }
    }

    override suspend fun getUserDetails(userName: String): LiveData<Response<UserDetails>> {
        return withContext(Dispatchers.IO) {
            networkDataSource.userDetails(userName)
            return@withContext networkDataSource.userDetails
        }
    }

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