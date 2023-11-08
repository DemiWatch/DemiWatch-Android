package com.project.demiwatch.features.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.demiwatch.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private var userUseCase: UserUseCase): ViewModel(){

    fun getTokenUser() = userUseCase.getTokenUser().asLiveData()

    fun registerUser(email: String, password: String) = userUseCase.registerUser(email, password).asLiveData()
}