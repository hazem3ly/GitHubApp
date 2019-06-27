package com.itg.githubapp.ui.fragments.repodetails

import androidx.lifecycle.ViewModel
import com.itg.githubapp.data.network.response.Repository
import com.itg.githubapp.data.repository.RepositoryApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class RepoDetailsViewModel(val repository: RepositoryApi) : ViewModel() {

    internal suspend fun getRepoDetails(repo: Repository) = GlobalScope.async {
        return@async repository.getRepoDetails(repo.owner.login, repo.name)
    }

}
