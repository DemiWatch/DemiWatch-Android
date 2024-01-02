package com.project.demiwatch.features.patient_detail.change_address

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.demiwatch.R
import com.project.demiwatch.core.utils.Resource
import com.project.demiwatch.core.utils.showToast
import com.project.demiwatch.databinding.ActivityChangeAddressBinding
import com.project.demiwatch.features.pick_location.PickLocationFragment
import com.project.demiwatch.features.pick_location.PickLocationViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ChangeAddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeAddressBinding
    private val changeAddressViewModel: ChangeAddressViewModel by viewModels()
    private val pickLocationViewModel: PickLocationViewModel by viewModels()

    private lateinit var savedToken: String
    private lateinit var savedPatientId: String
    private lateinit var savedUserId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChangeAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeAddressViewModel.getUserToken().observe(this) {
            savedToken = it
        }

        // TEMP SOLUTIONS FOR SAVED BUTTON BUG
        changeAddressViewModel.getIdUser().observe(this) {
            savedUserId = it

            changeAddressViewModel.getUser(savedToken, savedUserId).observe(this) { user ->
                when (user) {
                    is Resource.Error -> {
                        Timber.tag("ChangeAddressActivity").e(user.message)
                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Message -> {

                    }
                    is Resource.Success -> {
                        savedPatientId = user.data?.patientId.toString()

                        setupSaveButton(savedToken, savedPatientId)
                    }
                }
            }
        }
        // TEMP SOLUTIONS FOR SAVED BUTTON BUG

        changeAddressViewModel.getPatientId().observe(this) {
            savedPatientId = it

            setupInitialLocationNameData(savedToken, savedPatientId)
//            setupSaveButton(savedToken, savedPatientId)
        }


        setupActionBar()

        setupButtonBack()

        setupSetLocation()
    }

    private fun setupSetLocation() {
        binding.apply {
            edPatientAddressHomeLatitude.setOnClickListener {
                pickLocationViewModel.setPickedLocationType(1)
                pickLocationViewModel.setFromActivityType(2)

                supportFragmentManager.beginTransaction().add(
                    R.id.frame_container,
                    PickLocationFragment(),
                    PickLocationFragment::class.java.simpleName
                ).commit()
            }

            edPatientAddressHomeLongitude.setOnClickListener {
                pickLocationViewModel.setPickedLocationType(2)
                pickLocationViewModel.setFromActivityType(2)

                supportFragmentManager.beginTransaction().add(
                    R.id.frame_container,
                    PickLocationFragment(),

                    PickLocationFragment::class.java.simpleName
                ).commit()
            }

            edPatientAddressDestinationLatitude.setOnClickListener {
                pickLocationViewModel.setPickedLocationType(3)
                pickLocationViewModel.setFromActivityType(2)

                supportFragmentManager.beginTransaction().add(
                    R.id.frame_container,
                    PickLocationFragment(),
                    PickLocationFragment::class.java.simpleName
                ).commit()
            }

            edPatientAddressDestinationLongitude.setOnClickListener {
                pickLocationViewModel.setPickedLocationType(4)
                pickLocationViewModel.setFromActivityType(2)

                supportFragmentManager.beginTransaction().add(
                    R.id.frame_container,
                    PickLocationFragment(),
                    PickLocationFragment::class.java.simpleName
                ).commit()
            }
        }
    }

    private fun setupSaveButton(token: String, patientId: String) {
        Timber.tag("TEST").e("ini patient id " + patientId)

        binding.btnSave.setOnClickListener {
            val destinationName = binding.edNameDestination.text.toString()
            val homeName = binding.edNameHome.text.toString()
            val homeLatitude = binding.edPatientAddressHomeLatitude.text.toString()
            val homeLongitude = binding.edPatientAddressHomeLongitude.text.toString()
            val destinationLatitude = binding.edPatientAddressDestinationLatitude.text.toString()
            val destinationLongitude = binding.edPatientAddressDestinationLongitude.text.toString()

            if (destinationName.isNotEmpty() && homeName.isNotEmpty()
                && homeLatitude.isNotEmpty() && homeLongitude.isNotEmpty()
                && destinationLatitude.isNotEmpty() && destinationLongitude.isNotEmpty()
            ) {
                changeAddressViewModel.updatePatientLocations(
                    token,
                    patientId,
                    homeName,
                    homeLongitude.toDouble(),
                    homeLatitude.toDouble(),
                    destinationName,
                    destinationLongitude.toDouble(),
                    destinationLatitude.toDouble()
                ).observe(this) { patient ->
                    when (patient) {
                        is Resource.Error -> {
                            showLoading(false)
                            buttonEnabled(true)
                        }
                        is Resource.Loading -> {
                            showLoading(true)
                            buttonEnabled(false)
                        }
                        is Resource.Message -> {

                        }
                        is Resource.Success -> {
                            showLoading(false)
                            buttonEnabled(true)

                            showToast("Data lokasi pasien berhasil diperbaharui")
                        }
                    }
                }
            } else {
                showToast("Terjadi kesalahan, pastikan koneksi internet anda dan simpan ulang")
            }
        }
    }

    private fun setupInitialLocationNameData(token: String, patientId: String) {
        changeAddressViewModel.getPatient(token, patientId).observe(this) { patient ->
            when (patient) {
                is Resource.Error -> {
                    showLoading(false)
                    buttonEnabled(true)

                    Timber.tag("ChangeAddressActivity").e(patient.message)
                }
                is Resource.Loading -> {
                    showLoading(true)
                    buttonEnabled(false)
                }
                is Resource.Message -> {
                    Timber.tag("ChangeAddressActivity").d(patient.message)
                    showLoading(false)
                    buttonEnabled(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    buttonEnabled(true)

                    binding.apply {
                        edNameHome.setText(patient.data?.homeName)
                        edPatientAddressHomeLatitude.setText(patient.data?.latitudeDestination.toString())
                        edPatientAddressHomeLongitude.setText(patient.data?.longitudeDestination.toString())
                        edNameDestination.setText(patient.data?.destinationName)
                        edPatientAddressDestinationLatitude.setText(patient.data?.latitudeHome.toString())
                        edPatientAddressDestinationLongitude.setText(patient.data?.longitudeHome.toString())
                    }
                }
            }
        }
    }

    private fun setupButtonBack() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupActionBar() {
        supportActionBar?.hide()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun buttonEnabled(isEnabled: Boolean) {
        binding.btnSave.isEnabled = isEnabled
    }
}