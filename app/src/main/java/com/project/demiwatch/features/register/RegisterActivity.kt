package com.project.demiwatch.features.register

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
import com.project.demiwatch.databinding.ActivityRegisterBinding
import com.project.demiwatch.features.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        setupEditTextFormat()

        setButtonRegister()

        setButtonToLogin()
    }

    private fun setupEditTextFormat() {
        binding.edEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isEmpty()) {
                    binding.btnRegister.isEnabled = false
                }
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
                if (p0.toString().isEmpty()) {
                    binding.btnRegister.isEnabled = false
                }
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
        if (binding.edEmail.text.toString().isEmpty() and binding.edPass.text.toString()
                .isEmpty() and binding.edPassConfirm.text.toString().isEmpty()
        ) {
            buttonEnabled(false)
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edPass.text.toString()
            val passwordConfirm = binding.edPassConfirm.text.toString()

            if (email.isEmpty() and password.isEmpty() and passwordConfirm.isEmpty()) {
                buttonEnabled(false)
                showLongToast(getString(R.string.fill_data))
            } else if (password != passwordConfirm) {
                buttonEnabled(false)
                showLongToast(getString(R.string.pass_not_match))
            } else {
                buttonEnabled(true)
                showLongToast(getString(R.string.complete_register_acc))

                registerViewModel.registerUser(email, password).observe(this) { register ->
                    when (register) {
                        is Resource.Error -> {
                            showLoading(false)
                            buttonEnabled(true)

                            showLongToast("Terjadi kesalahan, silahkan lakukan registrasi ulang")
                        }
                        is Resource.Loading -> {
                            showLoading(true)
                            buttonEnabled(false)
                        }
                        is Resource.Message -> {
                            Timber.tag("RegisterActivity").d(register.message)
                        }
                        is Resource.Success -> {
                            showLoading(false)
                            buttonEnabled(true)

                            showLongToast(getString(R.string.user_registered))

                            val intentToLogin = Intent(this, LoginActivity::class.java)
                            startActivity(intentToLogin)
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun setButtonToLogin() {
        binding.tvToLogin.setOnClickListener {
            val intentToLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentToLogin)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun buttonEnabled(isEnabled: Boolean) {
        binding.btnRegister.isEnabled = isEnabled
    }

    private fun setupActionBar() {
        supportActionBar?.hide()
    }
}