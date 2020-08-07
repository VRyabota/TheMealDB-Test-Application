package com.vrb.apps.themealdb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vrb.apps.themealdb.data.models.Meal

@Dao
public interface MealDao {

    @Query("SELECT * FROM meals")
    suspend fun getStoredMeals(): List<Meal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun storeMeal(meal: Meal)
}