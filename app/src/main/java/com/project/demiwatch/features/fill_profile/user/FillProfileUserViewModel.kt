package com.project.demiwatch.features.fill_profile.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.demiwatch.core.domain.usecase.PatientUseCase
import com.project.demiwatch.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FillProfileUserViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val patientUseCase: PatientUseCase
) : ViewModel() {

    fun getUserId() = userUseCase.getIdUser().asLiveData()

    fun getUserToken() = userUseCase.getTokenUser().asLiveData()

    fun addUser(
        id: String,
        token: String,
        name: String,
        radius: String,
        status: String,
        phoneNumber: String
    ) = userUseCase.addUser(token, id, name, radius, status, phoneNumber).asLiveData()
}