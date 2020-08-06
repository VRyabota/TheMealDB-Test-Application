package com.vrb.apps.themealdb.data

import com.vrb.apps.themealdb.data.remote.IRemoteRepository
import com.vrb.apps.themealdb.data.remote.models.Meal
import com.vrb.apps.themealdb.data.remote.models.MealsList

class Repository constructor(
    private val remoteRepository: IRemoteRepository
) : IRepository {

    override suspend fun search(name: String): MealsList = remoteRepository.search(name)

}