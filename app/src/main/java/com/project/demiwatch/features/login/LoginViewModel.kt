package com.project.demiwatch.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.project.demiwatch.core.domain.model.Auth
import com.project.demiwatch.core.domain.usecase.UserUseCase
import com.project.demiwatch.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
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
}