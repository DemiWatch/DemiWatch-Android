package com.project.demiwatch.features.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.viewbinding.BuildConfig
import com.project.demiwatch.R
import com.project.demiwatch.databinding.ActivitySplashBinding
import com.project.demiwatch.features.dashboard.MainActivity
import com.project.demiwatch.features.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        splashViewModel.getUserToken().observe(this){token ->
            if(token != null){
                Handler(Looper.getMainLooper()).postDelayed({
                    val intentFormSplash = if (token != "") {
                        Intent(this, MainActivity::class.java)
                    } else {
                        Intent(this, LoginActivity::class.java)
                    }
                    startActivity(intentFormSplash)
                    intentFormSplash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    finish()
                }, DELAY.toLong())
            }
        }

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupActionBar(){
        supportActionBar?.hide()
    }

    companion object{
        const val DELAY = 3000
    }
}