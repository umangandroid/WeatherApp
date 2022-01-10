package com.umang.weatherapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Contains queries to operate on data stored of Air Quality for current and fore cast data
 *
 */
@Dao
interface AirQualityDao {
    @Query("select * from AirQualityDb where type = :dataType")
    fun getAllData(dataType: Int): List<AirQualityDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<AirQualityDb>)

    @Query("delete from AirQualityDb WHERE type = :dataType")
    fun deleteAirQualityData(dataType: Int)

}