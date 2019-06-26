package com.itg.githubapp.ui.fragments.home

import androidx.lifecycle.ViewModel
import com.itg.githubapp.data.repository.RepositoryApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class HomeViewModel(val repository: RepositoryApi) : ViewModel() {

    internal suspend fun getRepos(since: Int = 0) = GlobalScope.async {
        return@async repository.getRepos(since)
    }


    internal suspend fun searchRepos(query: String, page: Int = 0, perPage: Int = 20) = GlobalScope.async {
        return@async repository.searchRepos(query, page, perPage)
    }
}
