package com.vrb.apps.themealdb.data.remote.models

import com.google.gson.annotations.SerializedName

data class MealsList(
    @SerializedName("meals") val meals: List<Meal>
)