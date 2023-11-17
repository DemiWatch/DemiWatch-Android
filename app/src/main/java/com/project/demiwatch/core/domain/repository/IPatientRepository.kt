package com.project.demiwatch.core.domain.repository

import com.project.demiwatch.core.domain.model.Patient
import com.project.demiwatch.core.domain.model.PatientLocation
import com.project.demiwatch.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IPatientRepository {

    fun getLocationPatient(token: String): Flow<Resource<PatientLocation>>

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

    fun getPatient(id: String, token: String): Flow<Resource<Patient>>

}
