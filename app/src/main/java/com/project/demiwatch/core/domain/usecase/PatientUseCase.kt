package com.project.demiwatch.core.domain.usecase

import com.project.demiwatch.core.domain.model.PatientLocation
import com.project.demiwatch.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PatientUseCase {

    fun getLocationPatient(token: String): Flow<Resource<PatientLocation>>
}