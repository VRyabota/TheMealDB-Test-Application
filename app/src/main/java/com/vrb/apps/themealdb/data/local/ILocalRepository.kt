package com.vrb.apps.themealdb.data.local

import com.vrb.apps.themealdb.data.models.Meal

interface ILocalRepository {

    suspend fun getStoredMeals(): List<Meal>

    suspend fun storeMeal(meal: Meal)
}