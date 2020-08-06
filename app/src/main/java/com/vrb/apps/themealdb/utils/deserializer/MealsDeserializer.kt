package com.vrb.apps.themealdb.utils.deserializer

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.vrb.apps.themealdb.data.remote.models.Ingredient
import com.vrb.apps.themealdb.data.remote.models.Meal
import com.vrb.apps.themealdb.data.remote.models.MealsList
import java.lang.reflect.Type

class MealsDeserializer : JsonDeserializer<MealsList> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): MealsList {

        val result = mutableListOf<Meal>()

        val jsonObject = json?.asJsonObject
        val mealsArray = jsonObject?.getAsJsonArray("meals")
        val gson = Gson()

        val ingredientsList = mutableListOf<Ingredient>()

        mealsArray?.forEach {
            val meal = gson.fromJson(it, Meal::class.java)
            val currentJsonObject = it.asJsonObject

            for (i in 1 until 20) {

                val ingredientName = currentJsonObject.get("strIngredient$i").toString()
                val ingredientMeasure = currentJsonObject.get("strMeasure$i").toString()

                ingredientsList.add(Ingredient(ingredientName, ingredientMeasure))
            }

            meal.ingredients = ingredientsList
            result.add(meal)
        }

        return MealsList(result)
    }

}