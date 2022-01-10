package com.umang.weatherapp.data.models

import com.umang.weatherapp.data.db.AirQualityDb

/**
 * Used with AirQualityListAdapter to show list items
 *
 */
sealed class AirQualityModel{
    data class AirQualityItem(val airQualityDb: AirQualityDb) : AirQualityModel() // air quality item
    data class DateItem(val date: String) : AirQualityModel() // to group air quality items by date
    data class ErrorItem(val error :String) :AirQualityModel() // to show error
}
