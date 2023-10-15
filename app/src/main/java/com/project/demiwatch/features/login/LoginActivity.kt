package com.project.demiwatch.features.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import com.project.demiwatch.R
import com.project.demiwatch.core.utils.showLongToast
import com.project.demiwatch.databinding.ActivityLoginBinding
import com.project.demiwatch.features.dashboard.MainActivity
import com.project.demiwatch.features.register.RegisterActivity
import timber.log.Timber
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupEditTextFormat()

        setButtonToRegister()

        setButtonLogin()
    }

    private fun setupEditTextFormat() {
        binding.edEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().isEmpty()){
                    binding.btnLogin.isEnabled = false
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (!Patterns.EMAIL_ADDRESS.matcher(p0!!).matches()) {
                    binding.edEmail.error = getString(R.string.invalid_email)
                    binding.btnLogin.isEnabled = false
                } else binding.btnLogin.isEnabled =
                    !(binding.edPass.text.isNullOrEmpty())
            }
        })

        binding.edPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().isEmpty()){
                    binding.btnLogin.isEnabled = false
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().length < 8) {
                    binding.edPass.error = getString(R.string.invalid_password)
                    binding.btnLogin.isEnabled = false
                } else binding.btnLogin.isEnabled =
                    !(binding.edEmail.text.isNullOrEmpty())
            }
        })
    }

    private fun setButtonLogin() {
        if (binding.edEmail.text.toString().isEmpty() and binding.edPass.text.toString().isEmpty()){
            buttonEnabled(false)
        }

       binding.btnLogin.setOnClickListener {
           val email = binding.edEmail.text.toString()
           val password = binding.edPass.text.toString()

           if (email.isEmpty() or password.isEmpty()) {
               Timber.tag("TEST").d("%s%s", email.isEmpty().toString(), password.isEmpty().toString())
               showLongToast(getString(R.string.fill_data))
               buttonEnabled(false)
           } else {
               Timber.tag("TEST").d("%s%s", email.isEmpty().toString(), password.isEmpty().toString())
               buttonEnabled(true)

               val intentToHome = Intent(this, MainActivity::class.java)
               startActivity(intentToHome)
               finish()
           }
       }
    }

    private fun setButtonToRegister(){
        binding.tvToRegister.setOnClickListener {
            val intentToRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentToRegister)
            finish()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun buttonEnabled(isEnabled: Boolean) {
        binding.btnLogin.isEnabled = isEnabled
    }
}