package com.project.demiwatch.features.maps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.demiwatch.R
import com.project.demiwatch.core.utils.showToast
import com.project.demiwatch.databinding.FragmentYourPatientBinding

class YourPatientFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentYourPatientBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentYourPatientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtonRoute()
    }

    private fun setupButtonRoute() {
        binding.btnAccessRoute.setOnClickListener {
            activity?.showToast("Masuk")
        }
    }
}