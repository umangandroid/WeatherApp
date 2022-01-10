package com.umang.weatherapp.data

import com.umang.weatherapp.data.models.AirQualityResponse
import com.umang.weatherapp.data.models.DataResult
import javax.inject.Inject

/**
 * To make api calls for current and forecast data
 *
 * @property apiService
 */
class RemoteDataSourceImpl @Inject constructor(private val apiService: APIService) :RemoteDataSource(){
    /**
     * To get current air quality data from api
     *
     * @return : air quality list
     */
    override suspend fun getCurrentAirQualityData(): DataResult<AirQualityResponse> {
        return getResult { apiService.getCurrentAirQuality() }
    }

    /**
     * To get forecast air quality data from api
     *
     * @return :air quality list
     */
    override suspend fun getForeCastAirQualityData(): DataResult<AirQualityResponse> {
        return getResult { apiService.getForecastAirQuality() }
    }

}