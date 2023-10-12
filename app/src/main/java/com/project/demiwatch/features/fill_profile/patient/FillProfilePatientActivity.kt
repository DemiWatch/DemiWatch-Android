package com.project.demiwatch.features.fill_profile.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.demiwatch.R
import com.project.demiwatch.databinding.ActivityFillProfilePatientBinding

class FillProfilePatientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFillProfilePatientBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFillProfilePatientBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}