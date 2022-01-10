package com.umang.weatherapp.fakesource

import com.umang.weatherapp.data.LocalDataSource
import com.umang.weatherapp.data.db.AirQualityDb
import com.umang.weatherapp.data.models.DataResult
import com.umang.weatherapp.utils.CURRENT

class FakeLocalDataSource : LocalDataSource {
    private val fakeRepository = FakeRepository()
    override fun getAirQualityData(type: Int): DataResult<List<AirQualityDb>> {
        return if (type == CURRENT)
            fakeRepository.getFakeCurrentData()
        else
            fakeRepository.getFakeForecastData()
    }

    override suspend fun saveAirQualityData(list: List<AirQualityDb>) {

    }

    override fun deleteAirQualityData(type: Int) {

    }
}