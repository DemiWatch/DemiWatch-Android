package com.project.demiwatch.features.fill_profile.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.demiwatch.R
import com.project.demiwatch.databinding.ActivityFillProfileUserBinding

class FillProfileUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFillProfileUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFillProfileUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButtonBack()
    }

    private fun setupButtonBack() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}