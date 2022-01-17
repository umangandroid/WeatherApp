package com.umang.weatherapp.data

import com.umang.weatherapp.data.models.AirQualityResponse
import com.umang.weatherapp.data.models.DataResult
import com.umang.weatherapp.data.models.LatLongRequest
import org.json.JSONObject
import retrofit2.Response

/**
 * To get data from API
 *
 */
abstract class RemoteDataSource {
    abstract suspend fun getCurrentAirQualityData(latLongRequest: LatLongRequest): DataResult<AirQualityResponse>
    abstract suspend fun getForeCastAirQualityData(latLongRequest: LatLongRequest): DataResult<AirQualityResponse>

    /**
     * To parse api result in DataResult for further processing
     *
     * @param T : return type
     * @param call : api call
     * @return : DataResult (Success or Error)
     */
    suspend fun <T> getResult(call: suspend () -> Response<T>): DataResult<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    DataResult.Success(body)
                } else {
                    DataResult.Error(Exception("Error Occurred"))
                }
            } else {
                val errorMsg = JSONObject(response.errorBody()?.string())
                DataResult.Error(Exception(errorMsg.getString("message")))
            }

        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }
}