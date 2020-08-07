package com.vrb.apps.themealdb.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.vrb.apps.themealdb.data.models.typeconverters.IngredientsConverter
import com.vrb.apps.themealdb.data.models.typeconverters.NullValueConverter

@Entity(tableName = "meals")
@TypeConverters(IngredientsConverter::class)
data class Meal(
    @SerializedName("idMeal")
    @PrimaryKey
    @ColumnInfo(name = "meal_id")
    val id: String,

    @SerializedName("strMeal")
    @ColumnInfo(name = "meal_name")
    val name: String?,

    @SerializedName("strCategory")
    @ColumnInfo(name = "meal_category")
    val category: String?,

    @SerializedName("strArea")
    @ColumnInfo(name = "meal_area")
    val area: String?,

    @SerializedName("strInstructions")
    @ColumnInfo(name = "meal_instructions")
    val instructions: String?,

    @SerializedName("strMealThumb")
    @ColumnInfo(name = "meal_image_link")
    val imageLink: String?,

    @SerializedName("strTags")
    @ColumnInfo(name = "meal_tags")
    val tags: String?,

    @SerializedName("strYoutube")
    @ColumnInfo(name = "meal_youtube_link")
    val youtubeLink: String?,

    @ColumnInfo(name = "meal_ingredients")
    var ingredients: List<Ingredient>
) {

    override fun toString(): String {

        return "Selected meal: \n$id \n$name \n$category \n$area \n$instructions \n$imageLink \n$tags \n$youtubeLink \n${ingredients.joinToString(
            ";"
        )}}"
    }

}
