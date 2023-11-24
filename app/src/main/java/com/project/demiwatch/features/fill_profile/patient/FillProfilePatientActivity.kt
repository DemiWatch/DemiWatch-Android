package com.project.demiwatch.features.fill_profile.patient

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.demiwatch.R
import com.project.demiwatch.core.domain.model.PatientProfileCache
import com.project.demiwatch.core.utils.MapUtils
import com.project.demiwatch.core.utils.Resource
import com.project.demiwatch.core.utils.constants.patientSymptomItems
import com.project.demiwatch.core.utils.data_mapper.JsonMapper
import com.project.demiwatch.core.utils.showLongToast
import com.project.demiwatch.databinding.ActivityFillProfilePatientBinding
import com.project.demiwatch.features.dashboard.MainActivity
import com.project.demiwatch.features.fill_profile.user.FillProfileUserActivity
import com.project.demiwatch.features.pick_location.PickLocationFragment
import com.project.demiwatch.features.pick_location.PickLocationViewModel
import com.project.demiwatch.features.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FillProfilePatientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFillProfilePatientBinding
    private val fillProfilePatientViewModel: FillProfilePatientViewModel by viewModels()
    private val pickLocationViewModel: PickLocationViewModel by viewModels()

    private lateinit var savedToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFillProfilePatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        setupButtonBack()

        setupPickPatientSymptom()

        setupSaveButton()

        setupSetLocation()

        setLocationData()

        setupPatientProfileCache()
    }

    private fun setupPatientProfileCache() {
        fillProfilePatientViewModel.getCachePatientProfile().observe(this) {
            if (it != "" || it.isNotEmpty()) {
                val profile = JsonMapper.convertToPatientProfile(it)

                binding.apply {
                    edPatientFullName.setText(profile.name)
                    edPatientAge.setText(profile.age)
                    dropdownMenu.setText(profile.patientSymptoms)
                    edPatientNotes.setText(profile.notes)
                    edPatientWatchId.setText(profile.watchCode)
                    edPatientAddressHome.setText(profile.homeAddress)
                    edPatientAddressDestination.setText(profile.destinationAddress)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        setLocationData()
    }

    private fun setLocationData() {
        fillProfilePatientViewModel.getHomeLocationPatient().observe(this) { location ->
            if (location != null && location != "") {
                val position = MapUtils.convertToPoint(location)

                binding.apply {
                    edPatientAddressHomeLatitude.setText(position.latitude().toString())
                    edPatientAddressHomeLongitude.setText(position.longitude().toString())
                }
            }
        }

        fillProfilePatientViewModel.getDestinationLocationPatient().observe(this) { location ->
            if (location != null && location != "") {
                val position = MapUtils.convertToPoint(location)

                binding.apply {
                    edPatientAddressDestinationLatitude.setText(position.latitude().toString())
                    edPatientAddressDestinationLongitude.setText(position.longitude().toString())
                }
            }
        }
    }

    private fun setupSetLocation() {
        binding.edPatientAddressHomeLatitude.setOnClickListener {
            val name = binding.edPatientFullName.text.toString()
            val age = binding.edPatientAge.text.toString()
            val symptom = binding.dropdownMenu.text.toString()
            val patientNotes = binding.edPatientNotes.text.toString()
            val watchCode = binding.edPatientWatchId.text.toString()
            val homeAddress = binding.edPatientAddressHome.text.toString()
            val destinationAddress = binding.edPatientAddressDestination.text.toString()

            val cacheProfile = JsonMapper.convertPatientProfileToJson(
                PatientProfileCache(
                    name,
                    age,
                    symptom,
                    patientNotes,
                    watchCode,
                    homeAddress,
                    destinationAddress
                )
            )
            fillProfilePatientViewModel.cachePatientProfile(cacheProfile)

            pickLocationViewModel.setPickedLocationType(1)
            pickLocationViewModel.setFromActivityType(1)

            supportFragmentManager.beginTransaction().add(
                R.id.frame_container,
                PickLocationFragment(),
                PickLocationFragment::class.java.simpleName
            ).commit()
        }

        binding.edPatientAddressHomeLongitude.setOnClickListener {
            val name = binding.edPatientFullName.text.toString()
            val age = binding.edPatientAge.text.toString()
            val symptom = binding.dropdownMenu.text.toString()
            val patientNotes = binding.edPatientNotes.text.toString()
            val watchCode = binding.edPatientWatchId.text.toString()
            val homeAddress = binding.edPatientAddressHome.text.toString()
            val destinationAddress = binding.edPatientAddressDestination.text.toString()

            val cacheProfile = JsonMapper.convertPatientProfileToJson(
                PatientProfileCache(
                    name, age, symptom, patientNotes, watchCode, homeAddress, destinationAddress
                )
            )
            fillProfilePatientViewModel.cachePatientProfile(cacheProfile)

            pickLocationViewModel.setPickedLocationType(2)
            pickLocationViewModel.setFromActivityType(1)

            supportFragmentManager.beginTransaction().add(
                R.id.frame_container,
                PickLocationFragment(),

                PickLocationFragment::class.java.simpleName
            ).commit()
        }
        binding.edPatientAddressDestinationLatitude.setOnClickListener {
            val name = binding.edPatientFullName.text.toString()
            val age = binding.edPatientAge.text.toString()
            val symptom = binding.dropdownMenu.text.toString()
            val patientNotes = binding.edPatientNotes.text.toString()
            val watchCode = binding.edPatientWatchId.text.toString()
            val homeAddress = binding.edPatientAddressHome.text.toString()
            val destinationAddress = binding.edPatientAddressDestination.text.toString()

            val cacheProfile = JsonMapper.convertPatientProfileToJson(
                PatientProfileCache(
                    name, age, symptom, patientNotes, watchCode, homeAddress, destinationAddress
                )
            )
            fillProfilePatientViewModel.cachePatientProfile(cacheProfile)

            pickLocationViewModel.setPickedLocationType(3)
            pickLocationViewModel.setFromActivityType(1)

            supportFragmentManager.beginTransaction().add(
                R.id.frame_container,
                PickLocationFragment(),
                PickLocationFragment::class.java.simpleName
            ).commit()
        }

        binding.edPatientAddressDestinationLongitude.setOnClickListener {
            val name = binding.edPatientFullName.text.toString()
            val age = binding.edPatientAge.text.toString()
            val symptom = binding.dropdownMenu.text.toString()
            val patientNotes = binding.edPatientNotes.text.toString()
            val watchCode = binding.edPatientWatchId.text.toString()
            val homeAddress = binding.edPatientAddressHome.text.toString()
            val destinationAddress = binding.edPatientAddressDestination.text.toString()

            val cacheProfile = JsonMapper.convertPatientProfileToJson(
                PatientProfileCache(
                    name, age, symptom, patientNotes, watchCode, homeAddress, destinationAddress
                )
            )
            fillProfilePatientViewModel.cachePatientProfile(cacheProfile)

            pickLocationViewModel.setPickedLocationType(4)
            pickLocationViewModel.setFromActivityType(1)

            supportFragmentManager.beginTransaction().add(
                R.id.frame_container,
                PickLocationFragment(),
                PickLocationFragment::class.java.simpleName
            ).commit()
        }
    }


    private fun setupPickPatientSymptom() {
        val adapter = ArrayAdapter(this, R.layout.item_list_dropdown, patientSymptomItems)
        binding.dropdownMenu.setAdapter(adapter)
        binding.dropdownMenu.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.edPatientDisease.isHintEnabled = p0.isNullOrEmpty()
            }
        })
    }

    private fun setupSaveButton() {
        binding.btnSave.setOnClickListener {
            val name = binding.edPatientFullName.text.toString()
            val age = binding.edPatientAge.text.toString()
            val symptom = binding.dropdownMenu.text.toString()
            val patientNotes = binding.edPatientNotes.text.toString()
            val watchCode = binding.edPatientWatchId.text.toString()
            val homeAddress = binding.edPatientAddressHome.text.toString()
            val latitudeHomeAddress = binding.edPatientAddressHomeLatitude.text.toString()
            val longitudeHomeAddress = binding.edPatientAddressHomeLongitude.text.toString()
            val destinationAddress = binding.edPatientAddressDestination.text.toString()
            val latitudeDestinationAddress =
                binding.edPatientAddressDestinationLatitude.text.toString()
            val longitudeDestinationAddress =
                binding.edPatientAddressDestinationLongitude.text.toString()

            if (
                name.isNotEmpty() &&
                age.isNotEmpty() &&
                symptom.isNotEmpty() &&
                patientNotes.isNotEmpty() &&
                watchCode.isNotEmpty() &&
                homeAddress.isNotEmpty() &&
                destinationAddress.isNotEmpty() &&
                latitudeDestinationAddress.isNotEmpty() &&
                longitudeHomeAddress.isNotEmpty() &&
                latitudeHomeAddress.isNotEmpty() &&
                longitudeDestinationAddress.isNotEmpty()
            ) {
                fillProfilePatientViewModel.apply {
                    getUserToken().observe(this@FillProfilePatientActivity) {
                        savedToken = it
                        addPatient(
                            savedToken,
                            name,
                            age.toInt(),
                            symptom,
                            watchCode,
                            homeAddress,
                            longitudeHomeAddress.toDouble(),
                            latitudeHomeAddress.toDouble(),
                            destinationAddress,
                            longitudeDestinationAddress.toDouble(),
                            latitudeHomeAddress.toDouble(),
                            patientNotes
                        ).observe(this@FillProfilePatientActivity) { patient ->
                            when (patient) {
                                is Resource.Error -> {
                                    showLoading(false)
                                    buttonEnabled(true)

                                    showLongToast("Terjadi kesalahan, silahkan simpan ulang")
                                }
                                is Resource.Loading -> {
                                    showLoading(true)
                                    buttonEnabled(false)
                                }
                                is Resource.Message -> {
                                    Timber.tag("FillProfilePatientrActivity")
                                        .d(patient.message.toString())
                                }
                                is Resource.Success -> {
                                    showLoading(false)
                                    buttonEnabled(true)

                                    showLongToast(getString(R.string.patient_data_registered))
                                    fillProfilePatientViewModel.saveIdPatient(patient.data?.id!!)

                                    val intentToHome =
                                        Intent(
                                            this@FillProfilePatientActivity,
                                            MainActivity::class.java
                                        )

                                    startActivity(intentToHome)
                                    intentToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    finish()
                                }
                            }
                        }
                    }
                }
            } else {
                showLongToast(getString(R.string.fill_data))
            }
        }
    }

    private fun setupButtonBack() {
        binding.btnBack.setOnClickListener {
            fillProfilePatientViewModel.getUserId().observe(this) { user ->
                if (user != "") {
                    val intentToSplash = Intent(this, SplashActivity::class.java)
                    intentToSplash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intentToSplash)
                } else {
                    val intentToProfile = Intent(this, FillProfileUserActivity::class.java)
                    startActivity(intentToProfile)
                }
            }
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