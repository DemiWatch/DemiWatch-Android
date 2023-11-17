package com.project.demiwatch.features.fill_profile.patient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.demiwatch.core.domain.usecase.PatientUseCase
import com.project.demiwatch.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FillProfilePatientViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val patientUseCase: PatientUseCase
) : ViewModel() {

    fun getUserId() = userUseCase.getIdUser().asLiveData()

    fun getUserToken() = userUseCase.getTokenUser().asLiveData()

    fun addPatient(
        token: String,
        name: String,
        age: Int,
        symptom: String,
        watchCode: String,
        addressName: String,
        longitudeHome: Double,
        latitudeHome: Double,
        destinationName: String,
        longitudeDestination: Double,
        latitudeDestination: Double,
        notes: String
    ) = patientUseCase.addPatient(
        token,
        name,
        age,
        symptom,
        watchCode,
        addressName,
        longitudeHome,
        latitudeHome,
        destinationName,
        longitudeDestination,
        latitudeDestination,
        notes
    ).asLiveData()
}