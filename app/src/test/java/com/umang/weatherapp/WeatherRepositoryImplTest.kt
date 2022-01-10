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
        val resultFlow = weatherRepositoryImpl.getCurrentAirQualityData(true)
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
        val resultFlow = weatherRepositoryImpl.getForeCastAirQualityData(true)
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