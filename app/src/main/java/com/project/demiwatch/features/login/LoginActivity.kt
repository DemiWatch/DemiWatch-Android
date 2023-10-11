package com.project.demiwatch.features.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.demiwatch.R
import com.project.demiwatch.databinding.ActivityLoginBinding
import com.project.demiwatch.databinding.ActivitySplashBinding
import com.project.demiwatch.features.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}