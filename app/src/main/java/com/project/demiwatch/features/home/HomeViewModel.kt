package com.project.demiwatch.features.home

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.demiwatch.core.data.repository.PatientRepository
import com.project.demiwatch.core.domain.model.PatientLocation
import com.project.demiwatch.core.domain.usecase.PatientUseCase
import com.project.demiwatch.core.domain.usecase.UserUseCase
import com.project.demiwatch.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val patientUseCase: PatientUseCase,
    private val userUseCase: UserUseCase,
): ViewModel() {

    fun getTokenUser() = userUseCase.getTokenUser().asLiveData()

    private fun reqLocationPatient(token: String) = patientUseCase.getLocationPatient(token).asLiveData()

    fun getLocationPatient(): MediatorLiveData<Resource<PatientLocation>> = MediatorLiveData<Resource<PatientLocation>>().apply {
        addSource(getTokenUser()){
            token -> addSource(reqLocationPatient(token)){location ->
                value = location
        }
        }
    }

}