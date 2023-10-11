package com.project.demiwatch.features.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.project.demiwatch.R
import com.project.demiwatch.databinding.ActivitySplashBinding
import com.project.demiwatch.features.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val intentFromSplash = Intent(this, LoginActivity::class.java)
            startActivity(intentFromSplash)
            intentFromSplash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            finish()
        }, DELAY.toLong())
    }

    companion object{
        const val DELAY = 3000
    }
}