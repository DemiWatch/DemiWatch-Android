package com.project.demiwatch.features.patient_detail

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.demiwatch.core.domain.model.PatientLocation
import com.project.demiwatch.core.domain.usecase.PatientUseCase
import com.project.demiwatch.core.domain.usecase.UserUseCase
import com.project.demiwatch.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PatientDetailViewModel @Inject constructor(private var userUseCase: UserUseCase, private var patientUseCase: PatientUseCase): ViewModel() {
    fun getTokenUser() = userUseCase.getTokenUser().asLiveData()

    private fun reqLocationPatient(token: String) = patientUseCase.getLocationPatient(token).asLiveData()

    fun getLocationPatient(): MediatorLiveData<Resource<PatientLocation>> = MediatorLiveData<Resource<PatientLocation>>().apply {
        addSource(getTokenUser()){
                token -> addSource(reqLocationPatient(token)){
                location ->
            value = location
        }
        }
    }

}