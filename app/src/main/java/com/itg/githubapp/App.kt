package com.itg.githubapp

import android.app.Application
import com.itg.githubapp.data.network.*
import com.itg.githubapp.data.repository.RepositoryApi
import com.itg.githubapp.data.repository.RepositoryApiImpl
import com.itg.githubapp.ui.fragments.home.HomeViewModelFactory
import com.itg.githubapp.ui.fragments.repodetails.ReposDetailsViewModelFactory
import com.itg.githubapp.ui.fragments.userdetails.UserDetailsViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

class App : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@App))
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApiService(instance()) }
        bind<NetworkDataSource>() with singleton { NetworkDataSourceImpl(instance()) }
        bind<RepositoryApi>() with singleton { RepositoryApiImpl(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
        bind() from provider { UserDetailsViewModelFactory(instance()) }
        bind() from provider { ReposDetailsViewModelFactory(instance()) }
    }

}