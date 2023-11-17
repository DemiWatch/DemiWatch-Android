package com.project.demiwatch.core.domain.usecase

import com.project.demiwatch.core.domain.model.Patient
import com.project.demiwatch.core.domain.model.PatientLocation
import com.project.demiwatch.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PatientUseCase {

    fun getLocationPatient(token: String): Flow<Resource<PatientLocation>>

    //without profile picture
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
        notes: String,
    ): Flow<Resource<Patient>>

    fun updatePatient(
        id: String,
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
        notes: String,
    ): Flow<Resource<Patient>>

    fun getPatient(token: String, id: String): Flow<Resource<Patient>>
}