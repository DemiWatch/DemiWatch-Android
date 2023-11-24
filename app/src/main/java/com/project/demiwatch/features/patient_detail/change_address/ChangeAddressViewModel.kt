package com.project.demiwatch.features.patient_detail.change_address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.demiwatch.core.domain.usecase.PatientUseCase
import com.project.demiwatch.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangeAddressViewModel @Inject constructor(
    private var patientUseCase: PatientUseCase,
    private var userUseCase: UserUseCase,
) : ViewModel() {

    fun getUserToken() = userUseCase.getTokenUser().asLiveData()

    fun getPatientId() = patientUseCase.getIdPatient().asLiveData()

    fun getPatient(token: String, id: String) = patientUseCase.getPatient(token, id).asLiveData()

    fun getHomeLocationPatient() = patientUseCase.getHomeLocationPatient().asLiveData()

    fun getDestinationLocationPatient() =
        patientUseCase.getDestinationLocationPatient().asLiveData()

    fun updatePatientLocations(
        token: String,
        id: String,
        addressName: String,
        longitudeHome: Double,
        latitudeHome: Double,
        destinationName: String,
        longitudeDestination: Double,
        latitudeDestination: Double,
    ) = patientUseCase.updatePatientLocations(
        token,
        id,
        addressName,
        longitudeHome,
        latitudeHome,
        destinationName,
        longitudeDestination,
        latitudeDestination
    ).asLiveData()
}