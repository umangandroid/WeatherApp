package com.umang.weatherapp.ui

import androidx.lifecycle.*
import com.umang.weatherapp.R
import com.umang.weatherapp.data.DataResourceProvider
import com.umang.weatherapp.data.DataStoreManager
import com.umang.weatherapp.data.WeatherRepository
import com.umang.weatherapp.data.db.AirQualityDb
import com.umang.weatherapp.data.models.Components
import com.umang.weatherapp.data.models.DataResult
import com.umang.weatherapp.data.models.AirQualityModel
import com.umang.weatherapp.utils.EspressoIdlingResource
import com.umang.weatherapp.utils.HOUR_DIFF
import com.umang.weatherapp.utils.toDate
import com.umang.weatherapp.utils.toTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * ViewModel for displaying air quality list for current and forecast
 *
 * @property weatherRepository : to fetch air quality list from db/api
 * @property dataStoreManager : to fetch/save sync time
 * @property dataResourceProvider : to fetch string resources from strings.xml
 */
@HiltViewModel
class AirQualityListViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dataStoreManager: DataStoreManager,
    private val dataResourceProvider: DataResourceProvider
) :
    ViewModel() {
    private var mSearchStr: String = ""

    // to show common error as toast
    private val _commonError = Channel<String>()
    val commonError = _commonError.receiveAsFlow()

    //to pass Component object on click of current or forecast item
    private val _airQualityComponent = Channel<Components>()
    val airQualityComponent = _airQualityComponent.receiveAsFlow()

    //air quality flow list containing current/forecast items and error items
    var airQualityListFlow = MutableStateFlow<List<AirQualityModel>?>(null)

    //to hide progress bar
    val progressStatus = MutableStateFlow(false)

    //to hide show swipe refresh layout progress
    val isRefreshing = MutableStateFlow(false)

    //to get formatted sync time


    //not displaying sync time now as with search new data fetched from api every time
    val formattedSyncTime = ""
    /*Transformations.map(dataStoreManager.getSyncTime().asLiveData().distinctUntilChanged()) {
       formatSyncTime(it)
    }*/

    /**
     * Convert utc sync time formatted string
     * if last sync was < 5 minutes show "Just Now" if > 5 minutes show Minutes aga
     * @param it : utc sync time
     * @return : formatted sync time
     */
    private fun formatSyncTime(it: Long): String {
        val diff = System.currentTimeMillis() - it
        if (it > 0 && diff < HOUR_DIFF) {
            val minutes = diff / (1000 * 60)
            if (minutes < 5) {
                return dataResourceProvider.getString(R.string.just_now)
            }
            return dataResourceProvider.getString(R.string.ago, minutes)
        }
        return ""
    }

    /**
     * Sync air quality data from api is required if last sync time was before an hour
     *
     * @return : true if sync required
     */
    private suspend fun getIsSyncRequired(): Boolean {
        val result = dataStoreManager.getSyncTime().first()
        return (System.currentTimeMillis() - result > HOUR_DIFF)
    }

    //TODO :revisit this calling logic to make it proper for search
    fun getAirQualityDataFlow(isPullToRefresh: Boolean, searchStr: String) {
        mSearchStr = searchStr
        progressStatus.value = true
        viewModelScope.launch(Dispatchers.IO) {
            //get data from api if isPullToRefresh true or if last sync was before an hour
            EspressoIdlingResource.increment()
            val isGetFromAPI = isPullToRefresh || getIsSyncRequired()
            weatherRepository.getCurrentAirQualityData(isGetFromAPI, searchStr)
                .zip(
                    weatherRepository.getForeCastAirQualityData(
                        isGetFromAPI,
                        searchStr
                    )
                ) { currentAirQuality, foreCastAirQuality ->
                    return@zip processResponseFlow(
                        currentAirQuality,
                        foreCastAirQuality,
                        isGetFromAPI
                    )
                }.catch { e ->
                    e.localizedMessage?.let { _commonError.send(it) }
                    hideProgress()
                    EspressoIdlingResource.decrement()
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    airQualityListFlow.value = it
                    hideProgress()
                    EspressoIdlingResource.decrement()
                }
        }
    }

    /**
     * to hide progress views after data processed
     *
     */
    private fun hideProgress() {
        progressStatus.value = false
        isRefreshing.value = false
    }

    /**
     * to initiate fetch data from APIs
     *
     */
    fun refreshData() {
        isRefreshing.value = true
        getAirQualityDataFlow(true, mSearchStr)

    }

    /**
     * handle click events of current and forecast items
     *
     * @param components : Air component details
     * @return
     */
    fun openCurrentAirQualityDetails(components: Components) = viewModelScope.launch {
        _airQualityComponent.send(components)
    }


    /**
     * merge current and forecast list into single list.
     * parse error responses and add it to list
     * @param currentData : Current air quality list
     * @param foreCastData :Forecast air quality list
     * @param isGetFromAPI : True if data fetched from API
     * @return : merged list
     */
    private suspend fun processResponseFlow(
        currentData: DataResult<List<AirQualityDb>>,
        foreCastData: DataResult<List<AirQualityDb>>,
        isGetFromAPI: Boolean
    ): List<AirQualityModel> {
        // update sync time if both response received
        if (isGetFromAPI && (currentData is DataResult.Success && foreCastData is DataResult.Success)) {
            dataStoreManager.saveSyncTime(System.currentTimeMillis())
        }
        val responseList = ArrayList<AirQualityModel>()

        if (currentData is DataResult.Success) {//store current air quality data in list
            currentData.data[0].dateTime =
                currentData.data[0].dt.toDate() // parse utc time to sting date
            responseList.add(AirQualityModel.AirQualityItem(currentData.data[0]))
        } else if (currentData is DataResult.Error) { // add error item in list for current air quality data
            if (currentData.exception is UnknownHostException) { // if no internet throw error
                throw UnknownHostException(dataResourceProvider.getString(R.string.unable_to_connect))
            }
            dataStoreManager.saveSyncTime(0) //to get data from api in next attempt until data received successfully
            responseList.add(AirQualityModel.ErrorItem(dataResourceProvider.getString(R.string.unable_to_fetch_current_data)))
        }

        if (foreCastData is DataResult.Success) {//store forecast air quality data in list
            responseList.addAll(processForeCastData(foreCastData.data))
        } else if (foreCastData is DataResult.Error) { // add error item in list for forecast air quality data
            dataStoreManager.saveSyncTime(0)
            responseList.add(AirQualityModel.ErrorItem(dataResourceProvider.getString(R.string.unable_to_fetch_forecast_data)))
        }
        return responseList

    }

    /**
     * add date separators for forecast items and convert utc time to formatted time
     *
     * @param airQualityDataValues : db forecast list
     * @return : parsed forecast list
     */
    private fun processForeCastData(airQualityDataValues: List<AirQualityDb>): ArrayList<AirQualityModel> {
        val foreCastList = ArrayList<AirQualityModel>()
        var currentDate = airQualityDataValues[0].dt.toDate()
        foreCastList.add(AirQualityModel.DateItem(currentDate))
        airQualityDataValues.forEach {
            it.dateTime = it.dt.toTime()
            if (it.dt.toDate() != currentDate) {
                currentDate = it.dt.toDate()
                foreCastList.add(AirQualityModel.DateItem(currentDate))
            }
            foreCastList.add(AirQualityModel.AirQualityItem(it))
        }
        return foreCastList
    }


}