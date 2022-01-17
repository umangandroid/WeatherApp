package com.umang.weatherapp.data

import com.umang.weatherapp.data.models.AirQualityResponse
import com.umang.weatherapp.data.models.DataResult
import com.umang.weatherapp.data.models.LatLongRequest
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
    override suspend fun getCurrentAirQualityData(latLongRequest: LatLongRequest): DataResult<AirQualityResponse> {
        return getResult { apiService.getCurrentAirQuality(latLongRequest.latitude,latLongRequest.longitude) }
    }

    /**
     * To get forecast air quality data from api
     *
     * @return :air quality list
     */
    override suspend fun getForeCastAirQualityData(latLongRequest: LatLongRequest): DataResult<AirQualityResponse> {
        return getResult { apiService.getForecastAirQuality(latLongRequest.latitude,latLongRequest.longitude) }
    }

}