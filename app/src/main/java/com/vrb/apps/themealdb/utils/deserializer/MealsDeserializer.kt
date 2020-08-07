package com.vrb.apps.themealdb.utils.deserializer

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.vrb.apps.themealdb.data.models.Ingredient
import com.vrb.apps.themealdb.data.models.Meal
import com.vrb.apps.themealdb.data.models.MealsList
import java.lang.reflect.Type

class MealsDeserializer : JsonDeserializer<MealsList> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): MealsList {

        val result = mutableListOf<Meal>()

        val jsonObject = json?.asJsonObject
        val mealsArray = jsonObject?.get("meals")

        if (mealsArray?.isJsonNull == true){
            return MealsList(emptyList())
        } else {
            val gson = Gson()

            val ingredientsList = mutableListOf<Ingredient>()

            mealsArray?.asJsonArray?.forEach {
                val meal = gson.fromJson(it, Meal::class.java)
                val currentJsonObject = it.asJsonObject
                ingredientsList.clear()

                for (i in 1 until 21) {

                    val ingredientName = currentJsonObject.get("strIngredient$i").toString().replace("\"", "")
                    val ingredientMeasure = currentJsonObject.get("strMeasure$i").toString().replace("\"", "")

                    if (ingredientName.isNotBlank() && ingredientMeasure.isNotBlank()) {
                        ingredientsList.add(Ingredient(ingredientName, ingredientMeasure))
                    }
                }

                meal.ingredients = ingredientsList
                result.add(meal)
            }

            return MealsList(result)
        }
    }

}