package com.umang.weatherapp

import com.google.common.truth.Truth
import com.umang.weatherapp.data.WeatherRepositoryImpl
import com.umang.weatherapp.data.models.DataResult
import com.umang.weatherapp.fakesource.FakeLocalDataSource
import com.umang.weatherapp.fakesource.FakeRemoteSource
import com.umang.weatherapp.fakesource.FakeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 *  Unit test implementation for WeatherRepositoryImpl
 */
//TODO : update this class to map with search feature
@ExperimentalCoroutinesApi
class WeatherRepositoryImplTest {
    private lateinit var weatherRepositoryImpl: WeatherRepositoryImpl

    @Before
    fun setup() {
        weatherRepositoryImpl = WeatherRepositoryImpl(
            FakeRemoteSource(), FakeLocalDataSource(),
        )
    }

    @Test
    fun getAirQualityDataFlowTest() = runBlocking {
        val resultFlow = weatherRepositoryImpl.getCurrentAirQualityData(true, "23.5:23.5")
        val resultValue = resultFlow.first()
        assert(resultValue is DataResult.Success)
        val list = (resultValue as DataResult.Success).data
        Truth.assertThat(list.size).isEqualTo(1)
        Truth.assertThat(list[0].airQuality).isEqualTo(R.string.good)
        Truth.assertThat(list[0].airQualityColor)
            .isEqualTo(R.color.green)
    }

    @Test
    fun getForeCastAirQualityDataTest() = runBlocking {
        val resultFlow = weatherRepositoryImpl.getForeCastAirQualityData(true, "23.5:23.5")
        val resultValue = resultFlow.first()
        assert(resultValue is DataResult.Success)
        val list = (resultValue as DataResult.Success).data
        Truth.assertThat(list.size).isEqualTo(107)
        Truth.assertThat(list).isEqualTo(FakeRepository().getForecastDataAPI())
        Truth.assertThat(list[0].airQuality).isEqualTo(R.string.good)
        Truth.assertThat(list[0].airQualityColor)
            .isEqualTo(R.color.green)
    }


}