package com.itg.githubapp.ui.fragments.userdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itg.githubapp.data.repository.RepositoryApi

class UserDetailsViewModelFactory(
    private val repository: RepositoryApi
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserDetailsViewModel(repository) as T
    }
}