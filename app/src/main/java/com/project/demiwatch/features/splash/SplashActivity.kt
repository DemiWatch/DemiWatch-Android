package com.project.demiwatch.features.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.BuildConfig
import com.project.demiwatch.core.utils.Resource
import com.project.demiwatch.databinding.ActivitySplashBinding
import com.project.demiwatch.features.dashboard.MainActivity
import com.project.demiwatch.features.fill_profile.patient.FillProfilePatientActivity
import com.project.demiwatch.features.fill_profile.user.FillProfileUserActivity
import com.project.demiwatch.features.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val splashViewModel: SplashViewModel by viewModels()
    private lateinit var token: String
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        splashViewModel.apply {
            getUserToken().observe(this@SplashActivity) {
                token = it
            }

            getUserId().observe(this@SplashActivity) {
                userId = it

                checkUser(token, userId)
            }

        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun checkUser(checkToken: String, checkUserId: String) {
        splashViewModel.getUser(checkUserId, checkToken).observe(this@SplashActivity) { user ->
            when (user) {
                is Resource.Error -> {
                    val intentToLogin = Intent(this, LoginActivity::class.java)
                    startActivity(intentToLogin)
                }
                is Resource.Loading -> {

                }
                is Resource.Message -> {
                    Timber.tag("SplashActivtiy").d(user.message)
                }
                is Resource.Success -> {
                    Timber.tag("TEST").e(user.data?.patientId)

                    if (user.data?.name == "Nama User") {
                        val intentToFillProfileUser =
                            Intent(
                                this@SplashActivity,
                                FillProfileUserActivity::class.java
                            )

                        startActivity(intentToFillProfileUser)
                        finish()
                    } else if (user.data?.patientId == "Id Pasien") {
                        val intentToFillProfilePatient =
                            Intent(this@SplashActivity, FillProfilePatientActivity::class.java)

                        startActivity(intentToFillProfilePatient)
                        finish()
                    } else {
                        splashViewModel.savePatientId(user.data?.patientId!!)

                        val intentToHome =
                            Intent(this@SplashActivity, MainActivity::class.java)

                        startActivity(intentToHome)
                        finish()
                    }
                }
            }
        }
    }

    private fun setupActionBar() {
        supportActionBar?.hide()
    }

    companion object {
        const val DELAY = 3000
    }
}