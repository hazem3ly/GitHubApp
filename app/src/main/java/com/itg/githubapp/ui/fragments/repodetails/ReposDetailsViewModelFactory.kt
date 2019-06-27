package com.itg.githubapp.ui.fragments.repodetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itg.githubapp.data.repository.RepositoryApi

class ReposDetailsViewModelFactory(
    private val repository: RepositoryApi
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RepoDetailsViewModel(repository) as T
    }
}