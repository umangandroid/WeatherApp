package com.umang.weatherapp.data

import com.umang.weatherapp.BuildConfig
import com.umang.weatherapp.data.models.AirQualityResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val LATITUDE = 48.13743
const val LONGITUDE = 11.57549

/**
 * Contains all api calls
 *
 */
interface APIService {

    /**
     * To get current air quality data
     *
     * @param latitude : lat of munich
     * @param longitude : lon of munich
     * @param appId : api token
     * @return : Air Quality Response List
     */
    @GET("air_pollution")
    suspend fun getCurrentAirQuality(
        @Query("lat") latitude: Double = LATITUDE,
        @Query("lon") longitude: Double = LONGITUDE,
        @Query("appid") appId: String = BuildConfig.API_TOKEN
    ): Response<AirQualityResponse>

    /**
     * To get forecast of air quality data
     *
     * @param latitude : lat of munich
     * @param longitude : lon of munich
     * @param appId : api token
     * @return : Air Quality Response List
     */
    @GET("air_pollution/forecast")
    suspend fun getForecastAirQuality(
        @Query("lat") latitude: Double = LATITUDE,
        @Query("lon") longitude: Double = LONGITUDE,
        @Query("appid") appId: String =BuildConfig.API_TOKEN
    ): Response<AirQualityResponse>

}