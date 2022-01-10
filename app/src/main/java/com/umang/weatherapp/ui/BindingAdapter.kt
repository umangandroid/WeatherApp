package com.umang.weatherapp.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umang.weatherapp.data.models.AirQualityModel
import com.umang.weatherapp.ui.adapters.AirQualityListAdapter

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<AirQualityModel>?) {
    items?.let {
        (listView.adapter as AirQualityListAdapter).submitList(items)
    }
}