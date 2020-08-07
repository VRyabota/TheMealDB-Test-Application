package com.vrb.apps.themealdb.data.local

import com.vrb.apps.themealdb.data.local.db.MealDatabase
import com.vrb.apps.themealdb.data.models.Meal

class LocalRepository constructor(
    private val db: MealDatabase
) : ILocalRepository {

    override suspend fun getStoredMeals(): List<Meal> = db.mealDao().getStoredMeals()

    override suspend fun storeMeal(meal: Meal) = db.mealDao().storeMeal(meal)

}