package com.umang.weatherapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.SimpleItemAnimator
import com.umang.weatherapp.R
import com.umang.weatherapp.data.models.Components
import com.umang.weatherapp.databinding.FragmentAirQualityListBinding
import com.umang.weatherapp.ui.AirQualityListViewModel
import com.umang.weatherapp.ui.adapters.AirQualityListAdapter
import com.umang.weatherapp.utils.BUNDLE_DATA
import com.umang.weatherapp.utils.hideSoftKeyboard
import com.umang.weatherapp.utils.showKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * To show air quality data for current and forecast
 *
 */
@AndroidEntryPoint
class AirQualityListFragment : Fragment() {

    private val viewModel: AirQualityListViewModel by viewModels()
    private lateinit var fragmentAirQualityListBinding: FragmentAirQualityListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentAirQualityListBinding =
            FragmentAirQualityListBinding.inflate(inflater, container, false)
        //for data binding
        fragmentAirQualityListBinding.viewModel = viewModel
        fragmentAirQualityListBinding.lifecycleOwner = this.viewLifecycleOwner

        return fragmentAirQualityListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setActionListener()
        setupDataObservers()
        fragmentAirQualityListBinding.edtLatLong.showKeyboard()
    }

    /**
     * To search based on user input
     *
     */
    private fun setActionListener() {
        fragmentAirQualityListBinding.edtLatLong.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE ){
                fragmentAirQualityListBinding.edtLatLong.hideSoftKeyboard()
                viewModel.getAirQualityDataFlow(true,fragmentAirQualityListBinding.edtLatLong.text.toString().trim())
            }
            return@setOnEditorActionListener true
        }
    }

    /**
     * To handle one shot events
     *
     */
    private fun setupDataObservers() {
        lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    //to handle clicks of current and forecast item
                    viewModel.airQualityComponent.collect {
                        openAirQualityDetailsFragment(it)
                    }
                }
                launch {
                    //to show common error as toast
                    viewModel.commonError.collect {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }


    private fun setupAdapter() {
        //fragmentAirQualityListBinding.rvList.setHasFixedSize(true)
        (fragmentAirQualityListBinding.rvList.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
            false
        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.divider)
            ?.let { itemDecoration.setDrawable(it) }
        fragmentAirQualityListBinding.rvList.addItemDecoration(itemDecoration)
        val adapter = AirQualityListAdapter(viewModel)
        fragmentAirQualityListBinding.rvList.adapter = adapter

    }

    /**
     * To launch AirComponentDetailsFragment to show air component details
     *
     * @param components : Air component item
     */
    private fun openAirQualityDetailsFragment(components: Components) {
        val airQualityDetailsFragment = AirQualityDetailsFragment()
        val bundle = Bundle()
        bundle.putParcelable(BUNDLE_DATA, components)
        airQualityDetailsFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_left,
                R.anim.wait_anim,
                R.anim.wait_anim,
                R.anim.slide_right
            )
            .addToBackStack(null)
            .add(R.id.fragment_container_view, airQualityDetailsFragment)
            .commit()

    }


}