package com.project.demiwatch.features.navigation

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.demiwatch.core.domain.model.PatientLocation
import com.project.demiwatch.core.domain.usecase.PatientUseCase
import com.project.demiwatch.core.domain.usecase.UserUseCase
import com.project.demiwatch.core.utils.Resource
import com.project.demiwatch.core.utils.data_mapper.JsonMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private var userUseCase: UserUseCase,
    private var patientUseCase: PatientUseCase,
) : ViewModel() {

    fun getTokenUser() = userUseCase.getTokenUser().asLiveData()

    private fun reqLocationPatient(token: String, watchId: String) =
        patientUseCase.getLocationPatient(token, watchId).asLiveData()

    fun getCachePatientProfile() = patientUseCase.getCachePatientProfile().asLiveData()

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
}