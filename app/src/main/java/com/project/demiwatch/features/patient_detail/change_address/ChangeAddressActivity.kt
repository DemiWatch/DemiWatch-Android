package com.project.demiwatch.features.patient_detail.change_address

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.demiwatch.R
import com.project.demiwatch.databinding.ActivityChangeAddressBinding

class ChangeAddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeAddressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChangeAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        setupButtonBack()
    }

    private fun setupButtonBack() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupActionBar(){
        supportActionBar?.hide()
    }
}