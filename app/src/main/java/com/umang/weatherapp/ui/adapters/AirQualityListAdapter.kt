package com.umang.weatherapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umang.weatherapp.R
import com.umang.weatherapp.data.db.AirQualityDb
import com.umang.weatherapp.data.models.AirQualityModel
import com.umang.weatherapp.databinding.CurrentAirQualityItemBinding
import com.umang.weatherapp.databinding.ErrorItemBinding
import com.umang.weatherapp.databinding.ForecastItemBinding
import com.umang.weatherapp.databinding.ItemDateBinding
import com.umang.weatherapp.ui.AirQualityListViewModel

/**
 * To display air quality list items for current and forecast and to show error for current and
 * forecast air quality
 *
 * @property viewModel : instance of [AirQualityListViewModel]
 */
class AirQualityListAdapter(val viewModel: AirQualityListViewModel) :
    ListAdapter<AirQualityModel, RecyclerView.ViewHolder>(
        AirQualityItemComparator
    ) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val airQualityModel: AirQualityModel = getItem(position)

        when {
            airQualityModel is AirQualityModel.AirQualityItem && position == 0 -> { //for current air quality data
                val viewHolder = holder as CurrentAirQualityViewHolder
                viewHolder.bind(airQualityModel.airQualityDb, viewModel)

            }
            airQualityModel is AirQualityModel.AirQualityItem -> { //for forecast air quality item
                val viewHolder = holder as ForeCastViewModel
                viewHolder.bind(airQualityModel.airQualityDb, viewModel)

            }
            airQualityModel is AirQualityModel.DateItem -> { // to show date separator
                val viewHolder = holder as DateItemHolder
                viewHolder.bind(airQualityModel.date)

            }
            airQualityModel is AirQualityModel.ErrorItem -> { // to show error item
                val viewHolder = holder as ErrorItemHolder
                viewHolder.bind(airQualityModel.error)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        val airQualityModel: AirQualityModel = getItem(position)
        return when {
            airQualityModel is AirQualityModel.AirQualityItem && position == 0 -> R.layout.current_air_quality_item
            airQualityModel is AirQualityModel.AirQualityItem -> R.layout.forecast_item
            airQualityModel is AirQualityModel.DateItem -> R.layout.item_date
            else -> R.layout.error_item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.forecast_item -> {
                ForeCastViewModel(
                    ForecastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            R.layout.error_item -> {
                ErrorItemHolder(
                    ErrorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            R.layout.current_air_quality_item -> {
                CurrentAirQualityViewHolder(
                    CurrentAirQualityItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                DateItemHolder(
                    ItemDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
        }
    }

    class ForeCastViewModel(private val forecastItemBinding: ForecastItemBinding) :
        RecyclerView.ViewHolder(forecastItemBinding.root) {
        fun bind(airQualityData: AirQualityDb, airQualityListViewModel: AirQualityListViewModel) {
            forecastItemBinding.apply {
                airQuality = airQualityData
                viewModel = airQualityListViewModel
                executePendingBindings()
            }
        }
    }

    class CurrentAirQualityViewHolder(private val currentAirQualityItemBinding: CurrentAirQualityItemBinding) :
        RecyclerView.ViewHolder(currentAirQualityItemBinding.root) {
        fun bind(airQualityData: AirQualityDb, airQualityListViewModel: AirQualityListViewModel) {
            currentAirQualityItemBinding.apply {
                airQuality = airQualityData
                viewModel = airQualityListViewModel
                executePendingBindings()
            }
        }
    }


    class DateItemHolder(private val itemDateBinding: ItemDateBinding) :
        RecyclerView.ViewHolder(itemDateBinding.root) {
        fun bind(date: String) {
            itemDateBinding.apply {
                dateItem = date
                executePendingBindings()
            }
        }
    }

    class ErrorItemHolder(private val errorItemBinding: ErrorItemBinding) :
        RecyclerView.ViewHolder(errorItemBinding.root) {
        fun bind(errorStr: String) {
            errorItemBinding.apply {
                errorItem = errorStr
                executePendingBindings()
            }
        }
    }

    companion object {
        private val AirQualityItemComparator = object : DiffUtil.ItemCallback<AirQualityModel>() {
            override fun areItemsTheSame(oldItem: AirQualityModel, newItem: AirQualityModel): Boolean {
                return (oldItem is AirQualityModel.AirQualityItem && newItem is AirQualityModel.AirQualityItem &&
                        oldItem.airQualityDb.dt == newItem.airQualityDb.dt) ||
                        (oldItem is AirQualityModel.DateItem && newItem is AirQualityModel.DateItem &&
                                oldItem.date == newItem.date) || (oldItem is AirQualityModel.ErrorItem && newItem is AirQualityModel.ErrorItem &&
                        oldItem.error == newItem.error)
            }

            override fun areContentsTheSame(
                oldItem: AirQualityModel,
                newItem: AirQualityModel
            ): Boolean =
                oldItem == newItem
        }
    }

}