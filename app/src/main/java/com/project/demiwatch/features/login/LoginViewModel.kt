package com.project.demiwatch.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.project.demiwatch.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
) : ViewModel() {

    fun loginUser(email: String, password: String) =
        userUseCase.loginUser(email, password).asLiveData()

    fun saveUserToken(token: String) = viewModelScope.launch {
        userUseCase.saveTokenUser(token)
    }

    fun getUserToken() = userUseCase.getTokenUser().asLiveData()

    fun saveUserId(userId: String) = viewModelScope.launch {
        userUseCase.saveIdUser(userId)
    }

    fun getUserId() = userUseCase.getIdUser().asLiveData()

    fun getUser(id: String, token: String) = userUseCase.getUser(token, id).asLiveData()
}