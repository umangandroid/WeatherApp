package com.umang.weatherapp.data

import com.umang.weatherapp.data.db.AirQualityDb
import com.umang.weatherapp.data.db.AppDatabase
import com.umang.weatherapp.data.models.DataResult
import javax.inject.Inject

/**
 * To provide air quality data from db
 *
 * @property appDatabase
 */
class LocalDataSourceImpl @Inject constructor(private val appDatabase: AppDatabase) :LocalDataSource{

    /**
     * To get stored current and forecast air quality list from db
     *
     * @param type : current or forecast
     * @return : air quality list items
     */
    override fun getAirQualityData(type: Int): DataResult<List<AirQualityDb>> {
        return DataResult.Success(
            appDatabase.airQualityDao().getAllData(type)
        )
    }

    /**
     *To save air quality list received from api to db
     *
     * @param list : api air quality list
     */
    override suspend fun saveAirQualityData(list: List<AirQualityDb>) {
        appDatabase.airQualityDao().insertAll(list)
    }

    /**
     * to delete air quality list stored in db
     *
     * @param type : current or forecast
     */
    override fun deleteAirQualityData(type: Int) {
        appDatabase.airQualityDao().deleteAirQualityData(type)
    }
}