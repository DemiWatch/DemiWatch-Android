package com.project.demiwatch.features.patient_detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import com.project.demiwatch.databinding.ActivityPatientDetailBinding
import com.project.demiwatch.features.maps.MapsActivity
import com.project.demiwatch.features.patient_detail.change_address.ChangeAddressActivity

class PatientDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPatientDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPatientDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setupMap()

        setupActionBar()

        setupBackButton()

        setupFAB()
    }

//    private fun setupMap() {
//        binding.mapWrapper.setOnClickListener {
//            val intentToMap = Intent(this, MapsActivity::class.java)
//            startActivity(intentToMap)
//        }
//    }

    private fun setupFAB() {
        binding.fabChangeAddress.setOnClickListener {
            val intentToChangeAddress = Intent(this, ChangeAddressActivity::class.java)
            startActivity(intentToChangeAddress)
        }
    }

    private fun setupBackButton() {
       binding.btnBack.setOnClickListener {
           finish()
       }
    }

    private fun setupActionBar(){
        supportActionBar?.hide()
    }
}