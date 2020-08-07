package com.vrb.apps.themealdb.data.remote.api

import com.vrb.apps.themealdb.data.models.MealsList
import retrofit2.http.GET
import retrofit2.http.Query

interface MealDBApi {

    @GET("search.php")
    suspend fun search(@Query("s") name: String): MealsList
}