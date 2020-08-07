package com.vrb.apps.themealdb.di

import androidx.room.Room
import com.vrb.apps.themealdb.data.IRepository
import com.vrb.apps.themealdb.data.Repository
import com.vrb.apps.themealdb.data.local.ILocalRepository
import com.vrb.apps.themealdb.data.local.LocalRepository
import com.vrb.apps.themealdb.data.local.db.MealDatabase
import com.vrb.apps.themealdb.data.remote.IRemoteRepository
import com.vrb.apps.themealdb.data.remote.RemoteRepository
import com.vrb.apps.themealdb.ui.searchmeals.SearchMealsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { Room.databaseBuilder(get(), MealDatabase::class.java, "meals_database").build() }
    single<ILocalRepository> { LocalRepository(get()) }

    single<IRemoteRepository> { RemoteRepository(get()) }

    single<IRepository> { Repository(get(), get()) }

    viewModel { SearchMealsViewModel(get()) }
}