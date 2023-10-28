package com.project.demiwatch.features.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.project.demiwatch.R
import com.project.demiwatch.core.utils.showToast
import com.project.demiwatch.databinding.FragmentHomeBinding
import com.project.demiwatch.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() =_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSettingsPopUp()
    }

    private fun setupSettingsPopUp() {
        binding.btnMenu.setOnClickListener {
            val popUpMenu = PopupMenu(requireContext(), binding.btnMenu)
            popUpMenu.menuInflater.inflate(R.menu.profile_settings, popUpMenu.menu)

            popUpMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.settingsEdit ->{
                        requireContext().showToast("Edit")
                        true
                    }
                    R.id.settingsLogOut ->{
                        requireContext().showToast("Logout")
                        true
                    }
                    else -> false
                }
            }

            popUpMenu.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }


}