package com.project.demiwatch.features.home

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.project.demiwatch.core.domain.model.PatientLocation
import com.project.demiwatch.core.domain.usecase.PatientUseCase
import com.project.demiwatch.core.domain.usecase.UserUseCase
import com.project.demiwatch.core.utils.Resource
import com.project.demiwatch.core.utils.data_mapper.JsonMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val patientUseCase: PatientUseCase,
    private val userUseCase: UserUseCase,
) : ViewModel() {

    fun getTokenUser() = userUseCase.getTokenUser().asLiveData()

    fun getIdPatient() = patientUseCase.getIdPatient().asLiveData()

    fun getIdUser() = userUseCase.getIdUser().asLiveData()

    private fun reqLocationPatient(token: String, watchId: String) =
        patientUseCase.getLocationPatient(token, watchId).asLiveData()

    fun getLocationPatient(): MediatorLiveData<Resource<PatientLocation>> =
        MediatorLiveData<Resource<PatientLocation>>().apply {
            addSource(getTokenUser()) { token ->
                addSource(getCachePatientProfile()) { patient ->
                    if (patient != "" || patient.isNotEmpty()) {
                        val profile = JsonMapper.convertToPatientProfile(patient)

                        addSource(reqLocationPatient(token, profile.watchCode)) { location ->
                            value = location
                        }
                    }
                }
            }
        }


    fun getPatient(token: String, id: String) = patientUseCase.getPatient(token, id).asLiveData()

    fun cachePatientProfile(patientProfile: String) = viewModelScope.launch {
        patientUseCase.cachePatientProfile(patientProfile)
    }

    fun getCachePatientProfile() = patientUseCase.getCachePatientProfile().asLiveData()


    fun getUser(token: String, id: String) = userUseCase.getUser(token, id).asLiveData()

}