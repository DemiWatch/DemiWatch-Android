package com.project.demiwatch.core.domain.usecase

import com.project.demiwatch.core.data.repository.PatientRepository
import com.project.demiwatch.core.domain.model.Patient
import com.project.demiwatch.core.domain.model.PatientLocation
import com.project.demiwatch.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PatientInteractor @Inject constructor(private val patientRepository: PatientRepository) :
    PatientUseCase {
    override fun getLocationPatient(token: String): Flow<Resource<PatientLocation>> {
        return patientRepository.getLocationPatient(token)
    }

    override suspend fun saveIdPatient(patientId: String) {
        return patientRepository.saveIdPatient(patientId)
    }

    override fun getIdPatient(): Flow<String> {
        return patientRepository.getIdPatient()
    }

    override suspend fun saveHomeLocationPatient(homeLocation: String) {
        return patientRepository.saveHomeLocationPatient(homeLocation)
    }

    override fun getHomeLocationPatient(): Flow<String> {
        return patientRepository.getHomeLocationPatient()
    }

    override suspend fun saveDestinationLocationPatient(destinationLocation: String) {
        return patientRepository.saveDestinationLocationPatient(destinationLocation)
    }

    override fun getDestinationLocationPatient(): Flow<String> {
        return patientRepository.getDestinationLocationPatient()
    }

    override fun addPatient(
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
        notes: String
    ): Flow<Resource<Patient>> {
        return patientRepository.addPatient(
            token,
            name,
            age,
            symptom,
            watchCode,
            addressName,
            longitudeHome,
            latitudeHome,
            destinationName,
            longitudeDestination,
            latitudeDestination,
            notes
        )
    }

    override fun updatePatient(
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
        notes: String
    ): Flow<Resource<Patient>> {
        return patientRepository.updatePatient(
            id,
            token,
            name,
            age,
            symptom,
            watchCode,
            addressName,
            longitudeHome,
            latitudeHome,
            destinationName,
            longitudeDestination,
            latitudeDestination,
            notes
        )
    }

    override fun getPatient(token: String, id: String): Flow<Resource<Patient>> {
        return patientRepository.getPatient(id, token)
    }
}