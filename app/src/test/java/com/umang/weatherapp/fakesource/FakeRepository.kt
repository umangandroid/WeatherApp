package com.umang.weatherapp.fakesource

import com.google.gson.Gson
import com.umang.weatherapp.data.LocalDataSourceImpl
import com.umang.weatherapp.data.RemoteDataSourceImpl
import com.umang.weatherapp.data.WeatherRepository
import com.umang.weatherapp.data.WeatherRepositoryImpl
import com.umang.weatherapp.data.db.AirQualityDb
import com.umang.weatherapp.data.models.AirQualityResponse
import com.umang.weatherapp.data.models.DataResult
import com.umang.weatherapp.utils.CURRENT
import com.umang.weatherapp.utils.FORECAST
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.mockito.kotlin.mock

class FakeRepository :WeatherRepository {
    private val repoImpl = WeatherRepositoryImpl(mock<RemoteDataSourceImpl>(),mock<LocalDataSourceImpl>())
    override fun getCurrentAirQualityData(isRefresh: Boolean): Flow<DataResult<List<AirQualityDb>>> {
        return  flow{
           emit(getFakeCurrentData())
        }.flowOn(Dispatchers.IO)
    }

    override fun getForeCastAirQualityData(isRefresh: Boolean): Flow<DataResult<List<AirQualityDb>>> {
        return flow{
            emit(getFakeForecastData())
        }.flowOn(Dispatchers.IO)
    }

     fun getFakeCurrentData(): DataResult.Success<List<AirQualityDb>> {
        return DataResult.Success(getCurrentDataAPI())
    }

    private fun getCurrentDataAPI(): List<AirQualityDb> {
    val response =  Gson().fromJson(CURRENT_RESPONSE,AirQualityResponse::class.java)
        return repoImpl.parseResponse(response.airQualityDataValues, CURRENT)
    }

     fun getForecastDataAPI(): List<AirQualityDb> {
        val response =  Gson().fromJson(FORECAST_RESPONSE,AirQualityResponse::class.java)
        return repoImpl.parseResponse(response.airQualityDataValues, FORECAST)
    }

    fun getFakeForecastData(): DataResult.Success<List<AirQualityDb>> {
        return DataResult.Success(getForecastDataAPI())
    }




}