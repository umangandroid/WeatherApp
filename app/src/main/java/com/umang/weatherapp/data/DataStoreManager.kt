package com.umang.weatherapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.umang.weatherapp.utils.WEATHER_DATASTORE
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * To store/access sync time using DataStore
 *
 * @property context : Application context
 */
@Singleton
class DataStoreManager @Inject constructor (@ApplicationContext val context : Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = WEATHER_DATASTORE)
    companion object {
        val SYNC_TIME = longPreferencesKey("sync_time")
    }

    /**
     * To save sync time in data store
     *
     * @param dt : utc time
     */
    suspend fun saveSyncTime(dt :Long){
        context.dataStore.edit {
            it[SYNC_TIME] = dt
        }
    }

    /**
     * Get saved sync time
     *
     * @return : utc time
     */
    fun getSyncTime() = context.dataStore.data.map {
        it[SYNC_TIME]?:0
    }

}