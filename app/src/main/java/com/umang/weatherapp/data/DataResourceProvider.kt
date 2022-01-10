package com.umang.weatherapp.data

import android.content.Context
import com.umang.weatherapp.R
import com.umang.weatherapp.data.models.AirComponentItem
import com.umang.weatherapp.data.models.Components
import com.umang.weatherapp.utils.MICRO_GRAM
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Access string resources stored in strings.xml and to create list for Air component details
 *
 * @property appContext
 */
class DataResourceProvider @Inject constructor(@ApplicationContext val appContext : Context) {

    fun getString(resId :Int) :String{
        return appContext.getString(resId)
    }

    fun getString(resId: Int, args: Long): String {
        return appContext.getString(resId,args)
    }

    /**
     * Create list of items display in AirQualityDetailsFragment
     *
     * @param component : air component details received from API
     * @return : list of air component details
     */
    fun getAirComponentList(component : Components?) :List<AirComponentItem>{
        val list = ArrayList<AirComponentItem>()
        list.add(AirComponentItem(getString(R.string.co),"${component?.co} $MICRO_GRAM"))
        list.add(AirComponentItem(getString(R.string.no),"${component?.no} $MICRO_GRAM"))
        list.add(AirComponentItem(getString(R.string.no2),"${component?.no2} $MICRO_GRAM"))
        list.add(AirComponentItem(getString(R.string.o3),"${component?.o3} $MICRO_GRAM"))
        list.add(AirComponentItem(getString(R.string.so2),"${component?.so2} $MICRO_GRAM"))
        list.add(AirComponentItem(getString(R.string.pm2),"${component?.pm2_5} $MICRO_GRAM"))
        list.add(AirComponentItem(getString(R.string.pm10),"${component?.pm10} $MICRO_GRAM"))
        list.add(AirComponentItem(getString(R.string.nh3),"${component?.nh3} $MICRO_GRAM"))
        return list

    }
}