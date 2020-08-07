package com.vrb.apps.themealdb.data

import com.vrb.apps.themealdb.data.models.Meal
import com.vrb.apps.themealdb.data.models.MealsList

interface IRepository {

    // Api

    suspend fun search(name: String): MealsList

    // Local

    suspend fun getStoredMeals(): MealsList
    suspend fun storeMeal(meal: Meal)
}