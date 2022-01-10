package com.umang.weatherapp.data.models

/**
 * To parse and store response and handle error from AirQualityListViewModel
 *
 * @param
 */
sealed class DataResult<out R> {
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Error(val exception: Exception) : DataResult<Nothing>()
}