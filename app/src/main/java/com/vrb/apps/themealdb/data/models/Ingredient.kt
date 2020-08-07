package com.vrb.apps.themealdb.data.models

import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("ingredient_name") val name: String,
    @SerializedName("ingredient_measure") val measure: String
)