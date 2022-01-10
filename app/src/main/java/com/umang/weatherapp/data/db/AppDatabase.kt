package com.umang.weatherapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * The Room database for this app
 */
@Database(
    entities = [AirQualityDb::class
    ],
    version = 1, exportSchema = false
)
@TypeConverters(DataConverter::class) //type convertor for Components
abstract class AppDatabase : RoomDatabase() {
    abstract fun airQualityDao(): AirQualityDao


}
