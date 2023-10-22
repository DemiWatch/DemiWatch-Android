package com.project.demiwatch.features.fill_profile.patient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.demiwatch.R
import com.project.demiwatch.core.utils.showLongToast
import com.project.demiwatch.databinding.ActivityFillProfilePatientBinding
import com.project.demiwatch.features.dashboard.MainActivity

class FillProfilePatientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFillProfilePatientBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFillProfilePatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButtonBack()

        setupSaveButton()
    }

    private fun setupSaveButton() {
        val name = binding.edPatientFullName.text.toString()
        val age = binding.edPatientAge.text.toString()
        val status = binding.dropdownMenu.text.toString()
        val patientNotes = binding.edPatientNotes.text.toString()
        val watchId = binding.edPatientWatchId.text.toString()
        val homeAddress = binding.edPatientAddressHome.text.toString()
        val destinationAddress = binding.edPatientAddressDestination.text.toString()

        if(name.isNotEmpty() and age.isNotEmpty() and status.isNotEmpty() and patientNotes.isNotEmpty() and watchId.isNotEmpty() and homeAddress.isNotEmpty() and destinationAddress.isNotEmpty()){
            val intentToMain = Intent(this, MainActivity::class.java)
            startActivity(intentToMain)
        }else{
            showLongToast(getString(R.string.fill_data))
        }
    }

    private fun setupButtonBack() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}