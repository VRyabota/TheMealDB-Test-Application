package com.vrb.apps.themealdb.data

import com.vrb.apps.themealdb.data.local.ILocalRepository
import com.vrb.apps.themealdb.data.models.Meal
import com.vrb.apps.themealdb.data.models.MealsList
import com.vrb.apps.themealdb.data.remote.IRemoteRepository

class Repository constructor(
    private val localRepository: ILocalRepository,
    private val remoteRepository: IRemoteRepository
) : IRepository {

    // Api

    override suspend fun search(name: String): MealsList = remoteRepository.search(name)

    // Local

    override suspend fun getStoredMeals(): MealsList = MealsList(localRepository.getStoredMeals())
    override suspend fun storeMeal(meal: Meal) = localRepository.storeMeal(meal)

}