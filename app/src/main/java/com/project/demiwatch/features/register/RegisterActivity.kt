package com.project.demiwatch.features.register

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
import com.project.demiwatch.databinding.ActivityRegisterBinding
import com.project.demiwatch.features.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupEditTextFormat()

        setButtonRegister()

        setButtonToLogin()
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
                    binding.btnRegister.isEnabled = false
                } else binding.btnRegister.isEnabled =
                    !(binding.edPass.text.isNullOrEmpty() || binding.edPassConfirm.text.isNullOrEmpty())
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
                    binding.btnRegister.isEnabled = false
                } else binding.btnRegister.isEnabled =
                    !(binding.edEmail.text.isNullOrEmpty() || binding.edPassConfirm.text.isNullOrEmpty())
            }
        })

        binding.edPassConfirm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().length < 8) {
                    binding.edPassConfirm.error = getString(R.string.invalid_password)
                    binding.btnRegister.isEnabled = false
                } else binding.btnRegister.isEnabled =
                    !(binding.edPass.text.isNullOrEmpty() || binding.edEmail.text.isNullOrEmpty())
            }
        })
    }

    private fun setButtonRegister() {
        val email = binding.edEmail.text.toString()
        val password = binding.edPass.text.toString()
        val passwordConfirm = binding.edPassConfirm.text.toString()

        if (email.isEmpty() and password.isEmpty() and passwordConfirm.isEmpty()){
            buttonEnabled(false)
            showLongToast(getString(R.string.fill_data))
        }else if(password != passwordConfirm){
            buttonEnabled(false)
            showLongToast(getString(R.string.pass_not_match))
        }else{
            buttonEnabled(true)
            showLongToast(getString(R.string.complete_register_acc))

            val intentToLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentToLogin)
            finish()
        }
    }

    private fun setButtonToLogin() {
        binding.tvToLogin.setOnClickListener {
            val intentToLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentToLogin)
        }
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun buttonEnabled(isEnabled: Boolean){
        binding.btnRegister.isEnabled = isEnabled
    }
}