package com.project.demiwatch.features.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.project.demiwatch.R
import com.project.demiwatch.core.utils.Resource
import com.project.demiwatch.core.utils.showToast
import com.project.demiwatch.databinding.FragmentProfileBinding
import com.project.demiwatch.features.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() =_binding!!
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private lateinit var token: String
    private lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.getTokenUser().observe(this){
            token = it
        }

        profileViewModel.getIdUser().observe(this){
            userId = it

            setupUserData(token, userId)
        }


        setupSettingsPopUp()

        setupLogOut()
    }

    private fun setupUserData(token: String, userId: String) {
        profileViewModel.getUser(token, userId).observe(this){user ->
            when(user){
                is Resource.Error ->{
                    showLoading(false)
                }
                is Resource.Loading ->{
                    showLoading(true)
                }
                is Resource.Message ->{
                    Timber.tag("ProfileFragment").d(user.message)
                }
                is Resource.Success ->{
                    showLoading(false)

                    binding.apply {
                        tvProfileName.text = user.data?.name
                        tvProfileEmail.text = user.data?.email
                        tvProfilePhone.text = user.data?.teleNumber
                        tvProfileStatus.text = user.data?.status
                        tvProfileRadiusPatient.text = user.data?.safeRadius
                    }
                }
            }
        }
    }

    private fun setupLogOut() {
        binding.btnLogout.setOnClickListener {
            profileViewModel.deleteTokenUser()

            val intentToSplash = Intent(requireContext(), SplashActivity::class.java)
            startActivity(intentToSplash)
            activity?.finish()
        }
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}