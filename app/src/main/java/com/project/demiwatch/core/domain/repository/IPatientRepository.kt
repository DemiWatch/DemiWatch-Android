package com.project.demiwatch.core.domain.repository

import com.project.demiwatch.core.domain.model.PatientLocation
import com.project.demiwatch.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IPatientRepository {

    fun getLocationPatient(token: String): Flow<Resource<PatientLocation>>
}
