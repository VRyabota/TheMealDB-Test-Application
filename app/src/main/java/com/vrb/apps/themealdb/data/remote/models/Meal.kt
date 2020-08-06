package com.vrb.apps.themealdb.data.remote.models

import com.google.gson.annotations.SerializedName

data class Meal(
    @SerializedName("idMeal") val id: String,
    @SerializedName("strMeal") val name: String,
    @SerializedName("strCategory") val category: String,
    @SerializedName("strArea") val area: String,
    @SerializedName("strInstructions") val instructions: String,
    @SerializedName("strMealThumb") val imageLink: String,
    @SerializedName("strTags") val strTags: String,
    @SerializedName("strYoutube") val youtubeLink: String,
    var ingredients: List<Ingredient>
)