package com.itg.githubapp.data.repository

import androidx.lifecycle.LiveData
import com.itg.githubapp.data.network.response.Repository
import com.itg.githubapp.data.network.response.SearchReaslt
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface RepositoryApi {
   suspend fun getRepos(since: Int): LiveData<Response<List<Repository>>>
   suspend fun searchRepos(query: String,page:Int,perPage:Int): LiveData<Response<SearchReaslt>>
}