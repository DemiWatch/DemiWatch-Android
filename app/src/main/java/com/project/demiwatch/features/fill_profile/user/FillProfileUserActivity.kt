package com.project.demiwatch.features.fill_profile.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.demiwatch.R
import com.project.demiwatch.core.utils.showLongToast
import com.project.demiwatch.databinding.ActivityFillProfileUserBinding
import com.project.demiwatch.features.fill_profile.patient.FillProfilePatientActivity

class FillProfileUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFillProfileUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFillProfileUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        setupButtonBack()

        setupSaveButton()
    }

    private fun setupSaveButton() {
        binding.btnSave.setOnClickListener {
            val name = binding.edProfileName.text.toString()
            val phone = binding.edProfileTelp.text.toString()
            val status = binding.dropdownMenu.text.toString()
            val safeRadius = binding.edProfileSafeRadius.text.toString()


            if(name.isNotEmpty() and phone.isNotEmpty() and status.isNotEmpty() and safeRadius.isNotEmpty()){
                val intentToFillProfile = Intent(this, FillProfilePatientActivity::class.java)
                startActivity(intentToFillProfile)
            }else{
                showLongToast(getString(R.string.fill_data))
            }
        }
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