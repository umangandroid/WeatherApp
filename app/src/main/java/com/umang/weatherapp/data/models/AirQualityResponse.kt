package com.umang.weatherapp.data.models

import com.google.gson.annotations.SerializedName

/**
 * Parent Response item for api calls
 *
 * @property airQualityDataValues : list values
 */
data class AirQualityResponse(

    @SerializedName("list")
    val airQualityDataValues: List<AirQualityItem>
)