package com.vrb.apps.themealdb.data

import com.vrb.apps.themealdb.data.remote.models.Meal
import com.vrb.apps.themealdb.data.remote.models.MealsList

interface IRepository {

    suspend fun search(name: String): MealsList
}