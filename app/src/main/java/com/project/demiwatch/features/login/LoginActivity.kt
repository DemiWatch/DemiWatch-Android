package com.project.demiwatch.features.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.demiwatch.R
import com.project.demiwatch.core.utils.Resource
import com.project.demiwatch.core.utils.showLongToast
import com.project.demiwatch.databinding.ActivityLoginBinding
import com.project.demiwatch.features.dashboard.MainActivity
import com.project.demiwatch.features.fill_profile.user.FillProfileUserActivity
import com.project.demiwatch.features.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var token: String
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupActionBar()

        setupEditTextFormat()

        setButtonToRegister()

        setButtonLogin()
    }

    private fun setupEditTextFormat() {
        binding.edEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isEmpty()) {
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
                if (p0.toString().isEmpty()) {
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
        if (binding.edEmail.text.toString().isEmpty() and binding.edPass.text.toString()
                .isEmpty()
        ) {
            buttonEnabled(false)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edPass.text.toString()

            if (email.isEmpty() or password.isEmpty()) {
                showLongToast(getString(R.string.fill_data))
                buttonEnabled(false)
            } else {
                buttonEnabled(true)

                loginViewModel.loginUser(email, password).observe(this) { user ->
                    when (user) {
                        is Resource.Error -> {
                            showLoading(false)
                            buttonEnabled(true)

                            showLongToast("Terjadi kesalahan, silahkan masuk ulang")
                        }
                        is Resource.Loading -> {
                            showLoading(true)
                            buttonEnabled(false)
                        }
                        is Resource.Message -> {
                            Timber.tag("LoginActivity").d(user.message.toString())
                        }
                        is Resource.Success -> {
                            showLoading(false)

                            loginViewModel.apply {
                                saveUserToken(user.data?.token!!)
                                saveUserId(user.data.userId!!)
                                Timber.tag("TEST").e(user.data.userId)

                                getUserToken().observe(this@LoginActivity) {
                                    token = it
                                }

                                getUserId().observe(this@LoginActivity) {
                                    userId = it

                                    checkUser(token, userId)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun checkUser(checkToken: String, checkUserId: String) {
        loginViewModel.getUser(checkUserId, checkToken).observe(this@LoginActivity) { user ->
            when (user) {
                is Resource.Error -> {
                    showLoading(false)
                    buttonEnabled(true)

                    if (user.data?.error == "Data pasien tidak ditemukan") {
                        val intentToFillProfileUser =
                            Intent(
                                this@LoginActivity,
                                FillProfileUserActivity::class.java
                            )
                        startActivity(intentToFillProfileUser)
                        finish()
                    }
                }
                is Resource.Loading -> {
                    showLoading(true)
                    buttonEnabled(false)
                }
                is Resource.Message -> {
                    Timber.tag("LoginActivity").d(user.message.toString())
                }
                is Resource.Success -> {
                    showLoading(false)
                    buttonEnabled(true)

                    if (user.data?.name == "Nama User") {
                        val intentToFillProfileUser =
                            Intent(
                                this@LoginActivity,
                                FillProfileUserActivity::class.java
                            )

                        startActivity(intentToFillProfileUser)
                        finish()
                    } else {
                        val intentToHome =
                            Intent(
                                this@LoginActivity,
                                MainActivity::class.java
                            )

                        startActivity(intentToHome)
                        finish()
                    }
                }
            }
        }
    }

    private fun setButtonToRegister() {
        binding.tvToRegister.setOnClickListener {
            val intentToRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentToRegister)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun buttonEnabled(isEnabled: Boolean) {
        binding.btnLogin.isEnabled = isEnabled
    }

    private fun setupActionBar() {
        supportActionBar?.hide()
    }
}