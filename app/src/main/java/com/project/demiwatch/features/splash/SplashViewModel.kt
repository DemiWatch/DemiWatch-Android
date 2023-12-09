package com.project.demiwatch.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.project.demiwatch.core.domain.usecase.PatientUseCase
import com.project.demiwatch.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val patientUseCase: PatientUseCase
) : ViewModel() {

    fun getUserToken() = userUseCase.getTokenUser().asLiveData()

    fun getUserId() = userUseCase.getIdUser().asLiveData()

    fun savePatientId(patientId: String) = viewModelScope.launch {
        patientUseCase.saveIdPatient(patientId)
    }

    fun getUser(id: String, token: String) = userUseCase.getUser(token, id).asLiveData()
}