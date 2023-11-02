package com.project.demiwatch.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.demiwatch.core.data.repository.PatientRepository
import com.project.demiwatch.core.domain.usecase.PatientUseCase
import com.project.demiwatch.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val patientUseCase: PatientUseCase,
    private val userUseCase: UserUseCase,
): ViewModel() {

    fun getTokenUser() = userUseCase.getTokenUser().asLiveData()

    fun getLocationPatient(token: String) = patientUseCase.getLocationPatient(token).asLiveData()
}