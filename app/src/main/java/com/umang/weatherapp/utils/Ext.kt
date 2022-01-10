package com.umang.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * to convert utc to date string
 *
 * @return : date string
 */
fun Long.toDate() :String{
    val formatter =  SimpleDateFormat(DATE_FORMAT,Locale.US)
    return formatter.format(Date(this*1000))
}

/**
 * to convert utc to time string
 *
 * @return : time string
 */
fun Long.toTime() :String{
    val formatter =  SimpleDateFormat(TIME_FORMAT,Locale.US)
    return formatter.format(Date(this*1000)).uppercase()
}