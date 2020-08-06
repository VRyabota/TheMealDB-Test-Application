package com.vrb.apps.themealdb.di

import com.vrb.apps.themealdb.data.IRepository
import com.vrb.apps.themealdb.data.Repository
import com.vrb.apps.themealdb.data.remote.IRemoteRepository
import com.vrb.apps.themealdb.data.remote.RemoteRepository
import com.vrb.apps.themealdb.ui.searchmeals.SearchMealsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<IRemoteRepository> { RemoteRepository(get()) }
    single<IRepository> { Repository(get()) }

    viewModel { SearchMealsViewModel(get()) }
}