package com.umang.weatherapp

import com.google.common.truth.Truth.assertThat
import com.umang.weatherapp.data.DataResourceProvider
import com.umang.weatherapp.data.DataStoreManager
import com.umang.weatherapp.data.models.AirQualityModel
import com.umang.weatherapp.fakesource.FakeRepository
import com.umang.weatherapp.ui.AirQualityListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

/**
 *
 *  Unit test implementation for AirQualityListViewModel
 */
//TODO : update this class to map with search feature
@ExperimentalCoroutinesApi
class AirQualityListViewModelTest {
    private lateinit var viewModel: AirQualityListViewModel

    @Before
    fun setup() {
        val dataStoreManager = mock<DataStoreManager>()
        val dataResourceProvider =
            mock<DataResourceProvider>()
        val syncTimeFlow = MutableStateFlow(0)
        doReturn(syncTimeFlow).`when`(dataStoreManager).getSyncTime()
        viewModel = AirQualityListViewModel(
            FakeRepository(), dataStoreManager,
            dataResourceProvider
        )
    }

    @Test
    fun getAirQualityDataFlowTest() = runBlocking {
        viewModel.getAirQualityDataFlow(true, "23.5:23.5")
        Assert.assertEquals(true, viewModel.progressStatus.value)
        delay(1000)
        val list = viewModel.airQualityListFlow.first()
        assertThat(list?.size).isEqualTo(114)
        Assert.assertEquals(false, viewModel.progressStatus.value)

        //verify current air quality data
        val currentAirQualityItem = list?.get(0) is AirQualityModel.AirQualityItem
        assert(currentAirQualityItem)
        assertThat((list?.get(0) as AirQualityModel.AirQualityItem).airQualityDb.airQuality).isEqualTo(R.string.good)
        assertThat((list[0] as AirQualityModel.AirQualityItem).airQualityDb.airQualityColor).isEqualTo(R.color.green)

        //verify date separator
        val dateItem = list[1] is AirQualityModel.DateItem
        assert(dateItem)
        assertThat((list[1] as AirQualityModel.DateItem).date).isEqualTo("9/1/22")

        //verify forecast air quality data
        val forecastItem = list[2] is AirQualityModel.AirQualityItem
        assert(forecastItem)
        assertThat((list[2] as AirQualityModel.AirQualityItem).airQualityDb.airQuality).isEqualTo(R.string.good)
        assertThat((list[2] as AirQualityModel.AirQualityItem).airQualityDb.airQualityColor).isEqualTo(R.color.green)

    }

}