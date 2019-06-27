package com.itg.githubapp.ui.fragments.userdetails

import androidx.lifecycle.ViewModel
import com.itg.githubapp.data.network.response.Repository
import com.itg.githubapp.data.repository.RepositoryApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class UserDetailsViewModel(val repository: RepositoryApi) : ViewModel() {
    internal suspend fun getUserDetails(repo: Repository) = GlobalScope.async {
        return@async repository.getUserDetails(repo.owner.login)
    }

    internal suspend fun getUserRepos(repo: Repository, page: Int = 0) = GlobalScope.async {
        return@async repository.getUserRepos(repo.owner.login, page)
    }


}
