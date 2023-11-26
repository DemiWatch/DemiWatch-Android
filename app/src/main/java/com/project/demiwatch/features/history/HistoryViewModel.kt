package com.project.demiwatch.features.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.demiwatch.core.domain.usecase.PatientUseCase
import com.project.demiwatch.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private var userUseCase: UserUseCase,
    private var patientUseCase: PatientUseCase
) : ViewModel() {

    fun getTokenUser() = userUseCase.getTokenUser().asLiveData()

    fun getHistoryPatient(token: String) = patientUseCase.getHistoryPatient(token).asLiveData()
}