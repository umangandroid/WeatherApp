package com.umang.weatherapp.data

import com.umang.weatherapp.R
import com.umang.weatherapp.data.db.AirQualityDb
import com.umang.weatherapp.data.models.AirQualityItem
import com.umang.weatherapp.data.models.DataResult
import com.umang.weatherapp.data.models.LatLongRequest
import com.umang.weatherapp.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : WeatherRepository {

    override fun getCurrentAirQualityData(isRefresh: Boolean, searchStr: String): Flow<DataResult<List<AirQualityDb>>> {
        return flow {
            if (isRefresh) {
                val result = remoteDataSource.getCurrentAirQualityData(getRequestModel(searchStr))
                if (result is DataResult.Success) {
                    localDataSource.deleteAirQualityData(CURRENT)
                    localDataSource.saveAirQualityData(
                        parseResponse(
                            result.data.airQualityDataValues,
                            CURRENT
                        )
                    )
                    emit(localDataSource.getAirQualityData(CURRENT))
                } else {
                    emit(result as DataResult.Error)
                }
            } else {
                emit(localDataSource.getAirQualityData(CURRENT))
            }
        }.flowOn(Dispatchers.IO)
    }

    //TODO : revisit code if validations not handled
    private fun getRequestModel(searchStr: String): LatLongRequest {
        return LatLongRequest(searchStr.split(LATLONG_SEPARATOR)[0].toDouble(),searchStr.split(LATLONG_SEPARATOR)[1].toDouble())

    }

    override fun getForeCastAirQualityData(isRefresh: Boolean, searchStr: String): Flow<DataResult<List<AirQualityDb>>> {
        return flow {
            if (isRefresh) {
                val result = remoteDataSource.getForeCastAirQualityData(getRequestModel(searchStr))
                if (result is DataResult.Success) {
                    localDataSource.deleteAirQualityData(FORECAST)
                    localDataSource.saveAirQualityData(
                        parseResponse(
                            result.data.airQualityDataValues,
                            FORECAST
                        )
                    )
                    emit(localDataSource.getAirQualityData(FORECAST))
                } else {
                    emit(result as DataResult.Error)
                }
            } else {
                emit(localDataSource.getAirQualityData(FORECAST))
            }

        }.flowOn(Dispatchers.IO)
    }

    /**
     * Format api response item to store in db
     *
     * @param apiResponseItems : API response items
     * @param type : current or forecast
     * @return : list to store in db
     */
    fun parseResponse(apiResponseItems: List<AirQualityItem>, type: Int): List<AirQualityDb> {
        val list = ArrayList<AirQualityDb>()
        apiResponseItems.forEach {
            val item = AirQualityDb(
                type = type,
                airQuality = getFormattedAirQuality(it.main.aqi),
                airQualityColor = getColorCodeForAirQuality(it.main.aqi),
                dt = it.dt,
                components = it.components
            )
            list.add(item)
        }
        return list
    }

    /**
     * Get color code for air quality index
     *
     * @param aqi : air quality index
     * @return : color code for air quality index
     */
    private fun getColorCodeForAirQuality(aqi: Int): Int {
        return when (aqi) {
            1, 2 ->
                R.color.green
            3 -> R.color.yellow
            else -> R.color.red
        }
    }

    /**
     * Get string value for air quality index
     *
     * @param aqi : air quality index
     * @return : string value
     */
    private fun getFormattedAirQuality(aqi: Int): Int {
        return when (aqi) {
            1 ->
                R.string.good
            2 -> R.string.fair
            3 -> R.string.moderate
            4 -> R.string.poor
            else -> R.string.very_poor
        }
    }


}

