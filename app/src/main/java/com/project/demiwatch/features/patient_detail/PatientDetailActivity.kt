package com.project.demiwatch.features.patient_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.demiwatch.databinding.ActivityPatientDetailBinding

class PatientDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPatientDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPatientDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
    }

    private fun setupActionBar(){
        supportActionBar?.hide()
    }
}