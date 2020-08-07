package com.vrb.apps.themealdb.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vrb.apps.themealdb.data.local.dao.MealDao
import com.vrb.apps.themealdb.data.models.Meal

@Database(entities = [Meal::class], version = 1)
abstract class MealDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDao
}
