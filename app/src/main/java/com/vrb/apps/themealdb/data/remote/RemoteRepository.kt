package com.vrb.apps.themealdb.data.remote

import com.vrb.apps.themealdb.data.remote.api.MealDBApi
import com.vrb.apps.themealdb.data.remote.models.Meal
import com.vrb.apps.themealdb.data.remote.models.MealsList

class RemoteRepository constructor(
    private val mealDBApi: MealDBApi
) : IRemoteRepository {

    override suspend fun search(name: String): MealsList = mealDBApi.search(name)

}