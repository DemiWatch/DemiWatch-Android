package com.project.demiwatch.features.fill_profile.user

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.demiwatch.R
import com.project.demiwatch.core.utils.Resource
import com.project.demiwatch.core.utils.constants.userStatusItems
import com.project.demiwatch.core.utils.showLongToast
import com.project.demiwatch.databinding.ActivityFillProfileUserBinding
import com.project.demiwatch.features.fill_profile.patient.FillProfilePatientActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FillProfileUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFillProfileUserBinding
    private val fillProfileUserViewModel: FillProfileUserViewModel by viewModels()

    private lateinit var savedToken: String
    private lateinit var savedUserId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFillProfileUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        setupButtonBack()

        setupPickUserStatus()

        fillProfileUserViewModel.apply {
            getUserToken().observe(this@FillProfileUserActivity) {
                savedToken = it
            }
            getUserId().observe(this@FillProfileUserActivity) {
                savedUserId = it

                setupSaveButton(savedToken, savedUserId)
                setupInitialData(savedToken, savedUserId)
            }
        }
    }

    private fun setupInitialData(savedToken: String, savedUserId: String?) {
        fillProfileUserViewModel.getUser(savedToken, savedUserId!!)
            .observe(this@FillProfileUserActivity) { user ->
                when (user) {
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Message -> {

                    }
                    is Resource.Success -> {

                    }
                }
            }
    }

    private fun setupPickUserStatus() {
        val adapter = ArrayAdapter(this, R.layout.item_list_dropdown, userStatusItems)
        binding.dropdownMenu.setAdapter(adapter)
        binding.dropdownMenu.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.edProfileStatus.isHintEnabled = p0.isNullOrEmpty()
            }
        })
    }

    private fun setupSaveButton(savedToken: String, savedUserId: String) {
        binding.btnSave.setOnClickListener {
            val name = binding.edProfileName.text.toString()
            val phone = binding.edProfileTelp.text.toString()
            val status = binding.dropdownMenu.text.toString()
            val safeRadius = binding.edProfileSafeRadius.text.toString()

            if (name.isEmpty() && phone.isEmpty() && status.isEmpty() && safeRadius.isEmpty()) {
                showLongToast(getString(R.string.fill_data))
            } else if (name.isNotEmpty() && phone.isNotEmpty() && status.isNotEmpty() && safeRadius.isNotEmpty()) {
                fillProfileUserViewModel.addUser(
                    savedUserId,
                    savedToken,
                    name,
                    safeRadius,
                    status,
                    phone
                ).observe(this@FillProfileUserActivity) { user ->
                    when (user) {
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
                            Timber.tag("FillProfileUserActivity").d(user.message.toString())
                        }
                        is Resource.Success -> {
                            showLoading(false)
                            buttonEnabled(true)

                            showLongToast(getString(R.string.user_data_registered))

                            val intentToFillProfilePatient =
                                Intent(
                                    this@FillProfileUserActivity,
                                    FillProfilePatientActivity::class.java
                                )
                            startActivity(intentToFillProfilePatient)
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
            finish()
        }
    }

    private fun setupActionBar() {
        supportActionBar?.hide()
    }

    private fun buttonEnabled(isEnabled: Boolean) {
        binding.btnSave.isEnabled = isEnabled
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}