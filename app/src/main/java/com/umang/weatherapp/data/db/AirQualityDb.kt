package com.umang.weatherapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.umang.weatherapp.data.models.Components

/**
 * Db item for air quality used with AirQualityModel
 *
 * @property id : auto increment id
 * @property type : current or forecast
 * @property airQuality : string resource id for air quality
 * @property airQualityColor : color code for air quality (green, yellow, red)
 * @property dateTime : String value for [dt] for current air quality it is date for forecast it is time
 * @property dt : utc time received from api
 * @property components : air component data
 */
@Entity
data class AirQualityDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: Int,
    val airQuality: Int,
    val airQualityColor: Int,
    var dateTime: String = "",
    val dt: Long,
    val components: Components
)
