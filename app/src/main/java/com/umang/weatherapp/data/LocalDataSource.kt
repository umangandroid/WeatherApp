package com.umang.weatherapp.data

import com.umang.weatherapp.data.db.AirQualityDb
import com.umang.weatherapp.data.models.DataResult

/**
 * To get data from db
 *
 */
interface LocalDataSource {
    fun getAirQualityData(type :Int) : DataResult<List<AirQualityDb>>
    suspend fun saveAirQualityData(list: List<AirQualityDb>)
    fun deleteAirQualityData(type :Int)
}