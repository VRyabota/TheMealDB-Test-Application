package com.vrb.apps.themealdb.data.models.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.vrb.apps.themealdb.data.models.Ingredient
import timber.log.Timber

class IngredientsConverter {

    @TypeConverter
    fun fromIngredients(ingredients: List<Ingredient>): String {
        val string = ingredients.joinToString(";") { Gson().toJson(it) }
        return string
    }

    @TypeConverter
    fun toIngredients(ingredientsString: String) = ingredientsString.split(";").map { Gson().fromJson(it, Ingredient::class.java) }
}