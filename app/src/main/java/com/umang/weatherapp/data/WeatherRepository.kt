package com.umang.weatherapp.data

import com.umang.weatherapp.data.db.AirQualityDb
import com.umang.weatherapp.data.models.DataResult
import kotlinx.coroutines.flow.Flow

/**
 * Main source of data
 *
 */
interface WeatherRepository {

    fun getCurrentAirQualityData(isRefresh: Boolean) :Flow<DataResult<List<AirQualityDb>>>

     fun getForeCastAirQualityData(isRefresh: Boolean) :Flow<DataResult<List<AirQualityDb>>>


}