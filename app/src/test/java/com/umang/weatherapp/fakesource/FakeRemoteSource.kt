package com.umang.weatherapp.fakesource

import com.google.gson.Gson
import com.umang.weatherapp.data.RemoteDataSource
import com.umang.weatherapp.data.models.AirQualityResponse
import com.umang.weatherapp.data.models.DataResult
import com.umang.weatherapp.data.models.LatLongRequest

class FakeRemoteSource :RemoteDataSource() {
    override suspend fun getCurrentAirQualityData(searchStr: LatLongRequest): DataResult<AirQualityResponse> {
        val response =  Gson().fromJson(CURRENT_RESPONSE,AirQualityResponse::class.java)
        return  DataResult.Success(response)
    }

    override suspend fun getForeCastAirQualityData(searchStr: LatLongRequest): DataResult<AirQualityResponse> {
        val response =  Gson().fromJson(FORECAST_RESPONSE,AirQualityResponse::class.java)
        return  DataResult.Success(response)
    }
}