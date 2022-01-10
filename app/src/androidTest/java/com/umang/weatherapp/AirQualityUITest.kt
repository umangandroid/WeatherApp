package com.umang.weatherapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Includes different UI tests for Air Quality list
 */
@RunWith(AndroidJUnit4::class)
class AirQualityUITest : BaseTest() {


    @Test
    fun verifyAirQualityViewsInListTest() {
        verifyViewShowInList(R.id.rvList,0,R.id.tvAirQuality)
        verifyViewShowInList(R.id.rvList,0,R.id.tvAirQualityLabel)
        verifyViewShowInList(R.id.rvList,0,R.id.tvDate)

        verifyViewShowInList(R.id.rvList,1,R.id.tvSeparatorDate)

        verifyViewShowInList(R.id.rvList,2,R.id.tvAirQuality)
        verifyViewShowInList(R.id.rvList,2,R.id.tvTime)
    }

    @Test
    fun verifyCurrentAirComponentDetailsViewTest(){
        clickOnListItemView(0,R.id.conContainer,R.id.rvList)
        verifyViewInList(R.id.rvDetailsList,0,"CO (Carbon monoxide)")
        verifyViewShowInList(R.id.rvDetailsList,0,R.id.tvValue)

        verifyViewInList(R.id.rvDetailsList,7,"NH3 (Ammonia)")
        verifyViewShowInList(R.id.rvDetailsList,7,R.id.tvValue)
    }

    @Test
    fun verifyForecastAirComponentDetailsViewTest(){
        clickOnListItemView(2,R.id.conContainer,R.id.rvList)
        verifyViewInList(R.id.rvDetailsList,0,"CO (Carbon monoxide)")
        verifyViewShowInList(R.id.rvDetailsList,0,R.id.tvValue)

        verifyViewInList(R.id.rvDetailsList,7,"NH3 (Ammonia)")
        verifyViewShowInList(R.id.rvDetailsList,7,R.id.tvValue)
    }

}