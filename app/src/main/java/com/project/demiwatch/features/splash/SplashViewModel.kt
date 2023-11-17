package com.project.demiwatch.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.demiwatch.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

    fun getUserToken() = userUseCase.getTokenUser().asLiveData()

    fun getUserId() = userUseCase.getIdUser().asLiveData()

    fun getUser(id: String, token: String) = userUseCase.getUser(token, id).asLiveData()
}