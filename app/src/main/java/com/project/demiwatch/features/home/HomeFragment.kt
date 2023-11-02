package com.project.demiwatch.features.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.project.demiwatch.R
import com.project.demiwatch.core.utils.Resource
import com.project.demiwatch.core.utils.showLongToast
import com.project.demiwatch.databinding.FragmentHomeBinding
import com.project.demiwatch.features.maps.MapsActivity
import com.project.demiwatch.features.patient_detail.PatientDetailActivity
import timber.log.Timber

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() =_binding!!
    private val homeViewModel:HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPatientRouteCard()

        setupPatientListCard()

        setupPatientRoute()

//        homeViewModel.getTokenUser().observe(this){
//            Timber.tag("HomeFragment").d(it)
//            homeViewModel.getLocationPatient(it).observe(this){location ->
//                when(location){
//                    is Resource.Error -> {
//                        requireContext().showLongToast(location.toString())
//                    }
//                    is Resource.Loading -> {
//
//                    }
//                    is Resource.Message ->{
//
//                    }
//                    is Resource.Success ->{
//                        requireContext().showLongToast(location.toString())
//                    }
//                }
//
//            }
//        }

        setupMap()
    }

    private fun setupMap() {
        binding.mapView.setOnClickListener {
            val intentToMap = Intent(requireContext(), MapsActivity::class.java)
            startActivity(intentToMap)
        }
    }

    private fun setupPatientListCard() {
        binding.cardPatientList.setOnClickListener {
            val intentToPatientDetail = Intent(requireContext(), PatientDetailActivity::class.java)
            startActivity(intentToPatientDetail)
        }
    }

    private fun setupPatientRouteCard() {
        binding.cardPatientRoute.setOnClickListener {
            val intentToPatientDetail = Intent(requireContext(), PatientDetailActivity::class.java)
            startActivity(intentToPatientDetail)
        }
    }

    private fun setupPatientRoute() {
        binding.btnPatientRoute.setOnClickListener {
            val intentToMap = Intent(requireContext(), MapsActivity::class.java)
            startActivity(intentToMap)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}