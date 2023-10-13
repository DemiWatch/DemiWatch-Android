package com.project.demiwatch.features.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import com.project.demiwatch.R
import com.project.demiwatch.core.utils.showLongToast
import com.project.demiwatch.databinding.ActivityLoginBinding
import com.project.demiwatch.features.dashboard.MainActivity
import com.project.demiwatch.features.register.RegisterActivity

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
        val email = binding.edEmail.text.toString()
        val password = binding.edPass.text.toString()

        if(email.isEmpty() and password.isEmpty()){
            buttonEnabled(false)
            showLongToast(getString(R.string.fill_data))
        }else{
            buttonEnabled(true)

            val intentToHome = Intent(this, MainActivity::class.java)
            startActivity(intentToHome)
            finish()
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