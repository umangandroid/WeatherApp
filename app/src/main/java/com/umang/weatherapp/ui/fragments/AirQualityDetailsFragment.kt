package com.umang.weatherapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.umang.weatherapp.R
import com.umang.weatherapp.data.DataResourceProvider
import com.umang.weatherapp.data.models.Components
import com.umang.weatherapp.databinding.FragmentAirQualityDetailsBinding
import com.umang.weatherapp.ui.adapters.AirComponentAdapter
import com.umang.weatherapp.utils.BUNDLE_DATA
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * To show air component details
 *
 */
@AndroidEntryPoint
class AirQualityDetailsFragment : Fragment() {

    @Inject
    lateinit var dataResourceProvider: DataResourceProvider // to convert component details in list

    private lateinit var airQualityDetailsBinding :FragmentAirQualityDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        airQualityDetailsBinding = FragmentAirQualityDetailsBinding.inflate(layoutInflater, container, false)
        return airQualityDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setClickListeners()
    }

    private fun setClickListeners() {
        airQualityDetailsBinding.tvTitle.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setupAdapter() {
        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.divider)
            ?.let { itemDecoration.setDrawable(it) }
        airQualityDetailsBinding.rvDetailsList.addItemDecoration(itemDecoration)

        val adapter = AirComponentAdapter()
        airQualityDetailsBinding.rvDetailsList.adapter = adapter
        val components = arguments?.getParcelable<Components>(BUNDLE_DATA)
        adapter.submitList(dataResourceProvider.getAirComponentList(components))
    }


}