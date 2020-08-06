package com.vrb.apps.themealdb.data.remote

import com.vrb.apps.themealdb.data.remote.models.Meal
import com.vrb.apps.themealdb.data.remote.models.MealsList

interface IRemoteRepository {

    suspend fun search(name: String): MealsList
}