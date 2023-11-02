package com.project.demiwatch.core.domain.usecase

import com.project.demiwatch.core.data.repository.PatientRepository
import com.project.demiwatch.core.domain.model.PatientLocation
import com.project.demiwatch.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PatientInteractor @Inject constructor(private val patientRepository: PatientRepository): PatientUseCase {
    override fun getLocationPatient(token: String): Flow<Resource<PatientLocation>> {
        return patientRepository.getLocationPatient(token)
    }
}