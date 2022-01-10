package com.umang.weatherapp.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.umang.weatherapp.data.models.Components
import java.io.Serializable

/**
 * To Convert to and from Convertors object
 *
 */
class DataConverter : Serializable {
    /**
     * convert components to json string
     *
     * @param optionValues : component object
     * @return : json string
     */
    @TypeConverter
    fun fromWeatherValues(optionValues: Components): String {
        val gson = Gson()
        val type = object : TypeToken<Components?>() {}.type
        return gson.toJson(optionValues, type)
    }

    /**
     * convert string to component
     *
     * @param optionValuesString : json string
     * @return : Components object
     */
    @TypeConverter
    fun toWeatherValuesList(optionValuesString: String): Components {
        val gson = Gson()
        val type = object : TypeToken<Components?>() {}.type
        return gson.fromJson(optionValuesString, type)
    }
}