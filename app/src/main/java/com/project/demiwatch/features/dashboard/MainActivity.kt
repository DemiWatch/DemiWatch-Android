package com.project.demiwatch.features.dashboard

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.Data
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.demiwatch.R
import com.project.demiwatch.core.utils.Resource
import com.project.demiwatch.core.utils.notifications.NOTIFICATION_CHANNEL_ID
import com.project.demiwatch.core.utils.notifications.NotificationWorker
import com.project.demiwatch.core.utils.popup.PopUpDialog
import com.project.demiwatch.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val homeViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupBottomNav()

        setupActionBar()

        setupNotifications()

        setupPopUp()
    }

    private fun setupPopUp() {
        homeViewModel.getLocationPatient().observe(this) { patient ->
            when (patient) {
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
                is Resource.Message -> {

                }
                is Resource.Success -> {
                    if (patient.data?.emergency == true) {
                        PopUpDialog().show(supportFragmentManager, "Patient Emergency Dialog")
                    }
                }
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {

            } else {

            }
        }

    private fun setupNotifications() {
        val channelName = getString(R.string.notify_channel_name)
        val data =
            Data.Builder().putString(NOTIFICATION_CHANNEL_ID, channelName).build()

        val periodicWork = PeriodicWorkRequest.Builder(
            NotificationWorker::class.java,
            15,
            TimeUnit.MINUTES
        ).setInputData(data).build()

        WorkManager.getInstance(this).enqueue(periodicWork)

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun setupBottomNav() {
        val navView: BottomNavigationView = binding.navView
        val navViewController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration.Builder(
            setOf(
                R.id.homeFragment,
                R.id.historyFragment,
                R.id.profileFragment,
            )
        ).build()

        setupActionBarWithNavController(navViewController, appBarConfiguration)
        navView.setupWithNavController(navViewController)
    }

    private fun setupActionBar() {
        supportActionBar?.hide()
    }
}