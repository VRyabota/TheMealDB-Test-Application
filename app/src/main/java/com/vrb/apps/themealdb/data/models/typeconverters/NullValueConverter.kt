package com.vrb.apps.themealdb.data.models.typeconverters

import androidx.room.TypeConverter

class NullValueConverter {

    @TypeConverter
    fun convertNulls(field: Any?): Any {
        return field ?: ""
    }
}