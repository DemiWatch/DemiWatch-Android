package com.project.demiwatch.features.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.demiwatch.R
import com.project.demiwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupBottomNav()

        setupActionBar()
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

    private fun setupActionBar(){
        supportActionBar?.hide()
    }
}