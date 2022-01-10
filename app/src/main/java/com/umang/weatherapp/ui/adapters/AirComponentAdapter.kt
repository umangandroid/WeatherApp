package com.umang.weatherapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umang.weatherapp.data.models.AirComponentItem
import com.umang.weatherapp.databinding.AirComponentItemBinding

/**
 * Adapter to display air component details
 *
 */
class AirComponentAdapter : RecyclerView.Adapter<AirComponentAdapter.AirComponentHolder>() {

    val list = ArrayList<AirComponentItem>()

    class AirComponentHolder(private val airComponentItemBinding: AirComponentItemBinding) :
        RecyclerView.ViewHolder(airComponentItemBinding.root) {
        fun bind(airComponentItem: AirComponentItem) {
            airComponentItemBinding.apply {
                component = airComponentItem
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirComponentHolder {
        return AirComponentHolder(
            AirComponentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AirComponentHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun submitList(airComponentList: List<AirComponentItem>) {
        list.clear()
        list.addAll(airComponentList)
        notifyDataSetChanged()
    }
}