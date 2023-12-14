package com.project.demiwatch.core.domain.usecase

import com.project.demiwatch.core.domain.model.Patient
import com.project.demiwatch.core.domain.model.PatientHistory
import com.project.demiwatch.core.domain.model.PatientLocation
import com.project.demiwatch.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PatientUseCase {

    fun getLocationPatient(token: String, watchId: String): Flow<Resource<PatientLocation>>

    suspend fun saveIdPatient(patientId: String)

    fun getIdPatient(): Flow<String>

    suspend fun saveHomeLocationPatient(homeLocation: String)

    fun getHomeLocationPatient(): Flow<String>

    suspend fun saveDestinationLocationPatient(destinationLocation: String)

    fun getDestinationLocationPatient(): Flow<String>

    suspend fun cachePatientProfile(patientProfile: String)

    fun getCachePatientProfile(): Flow<String>

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

    fun updatePatientLocations(
        token: String,
        id: String,
        addressName: String,
        longitudeHome: Double,
        latitudeHome: Double,
        destinationName: String,
        longitudeDestination: Double,
        latitudeDestination: Double,
    ): Flow<Resource<Patient>>

    fun getHistoryPatient(token: String, watchId: String): Flow<Resource<PatientHistory>>
}