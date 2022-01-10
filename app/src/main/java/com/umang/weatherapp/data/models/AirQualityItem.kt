package com.umang.weatherapp.data.models

/**
 * API Response item for air quality
 *
 * @property components : air quality components
 * @property dt : utc time
 * @property main : Air Quality number
 */
data class AirQualityItem(
    val components: Components,
    val dt: Long,
    val main: Main
)