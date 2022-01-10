package com.umang.weatherapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Air component details in micro gram
 *
 * @property co
 * @property nh3
 * @property no
 * @property no2
 * @property o3
 * @property pm10
 * @property pm2_5
 * @property so2
 */
@Parcelize
data class Components(
    val co: Double,
    val nh3: Double,
    val no: Double,
    val no2: Double,
    val o3: Double,
    val pm10: Double,
    val pm2_5: Double,
    val so2: Double
) : Parcelable