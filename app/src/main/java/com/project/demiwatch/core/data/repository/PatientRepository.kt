package com.project.demiwatch.core.data.repository

import com.project.demiwatch.core.data.source.local.LocalDataSource
import com.project.demiwatch.core.data.source.remote.NetworkBoundResource
import com.project.demiwatch.core.data.source.remote.datasource.RemoteDataSource
import com.project.demiwatch.core.data.source.remote.network.ApiResponse
import com.project.demiwatch.core.data.source.remote.response.patient.PatientHistoryResponse
import com.project.demiwatch.core.data.source.remote.response.patient.PatientLocationResponse
import com.project.demiwatch.core.data.source.remote.response.patient.PatientResponse
import com.project.demiwatch.core.domain.model.Patient
import com.project.demiwatch.core.domain.model.PatientAddress
import com.project.demiwatch.core.domain.model.PatientHistory
import com.project.demiwatch.core.domain.model.PatientLocation
import com.project.demiwatch.core.domain.repository.IPatientRepository
import com.project.demiwatch.core.utils.Resource
import com.project.demiwatch.core.utils.data_mapper.JsonMapper
import com.project.demiwatch.core.utils.data_mapper.PatientDataMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PatientRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : IPatientRepository {
    override fun getLocationPatient(
        token: String,
        watchId: String
    ): Flow<Resource<PatientLocation>> {
        return object : NetworkBoundResource<PatientLocation, PatientLocationResponse>() {
            override suspend fun fetchFromApi(response: PatientLocationResponse): PatientLocation {
                return PatientDataMapper.mapPatientLocationResponseToDomain(response)
            }

            override suspend fun createCall(): Flow<ApiResponse<PatientLocationResponse>> {
                return remoteDataSource.getLocationPatient(token, watchId)
            }

        }.asFlow()
    }

    override suspend fun saveIdPatient(patientId: String) {
        return localDataSource.saveIdPatient(patientId)
    }

    override fun getIdPatient(): Flow<String> {
        return localDataSource.getIdPatient()
    }

    override suspend fun saveHomeLocationPatient(homeLocation: String) {
        return localDataSource.saveHomeLocationPatient(homeLocation)
    }

    override fun getHomeLocationPatient(): Flow<String> {
        return localDataSource.getHomeLocationPatient()
    }

    override suspend fun saveDestinationLocationPatient(destinationLocation: String) {
        return localDataSource.saveDestinationLocationPatient(destinationLocation)

    }

    override fun getDestinationLocationPatient(): Flow<String> {
        return localDataSource.getDestinationLocationPatient()
    }

    override suspend fun cachePatientProfile(patientProfile: String) {
        return localDataSource.cachePatientProfile(patientProfile)
    }

    override fun getCachePatientProfile(): Flow<String> {
        return localDataSource.getCachePatientProfile()
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
        return object : NetworkBoundResource<Patient, PatientResponse>() {
            override suspend fun fetchFromApi(response: PatientResponse): Patient {
                return PatientDataMapper.mapPatientResponseToDomain(response)
            }

            override suspend fun createCall(): Flow<ApiResponse<PatientResponse>> {
                val homeAddress = JsonMapper.convertPatientAddressToJSON(
                    PatientAddress(
                        addressName,
                        longitudeHome,
                        latitudeHome,
                    )
                )

                val destinationAddress = JsonMapper.convertPatientAddressToJSON(
                    PatientAddress(
                        destinationName,
                        longitudeDestination,
                        latitudeDestination,
                    )
                )

                return remoteDataSource.addPatient(
                    token,
                    name,
                    age,
                    symptom,
                    notes,
                    watchCode,
                    homeAddress,
                    destinationAddress
                )
            }
        }.asFlow()
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
        return object : NetworkBoundResource<Patient, PatientResponse>() {
            override suspend fun fetchFromApi(response: PatientResponse): Patient {
                return PatientDataMapper.mapPatientResponseToDomain(response)
            }

            override suspend fun createCall(): Flow<ApiResponse<PatientResponse>> {
                val homeAddress = JsonMapper.convertPatientAddressToJSON(
                    PatientAddress(
                        addressName,
                        longitudeHome,
                        latitudeHome,
                    )
                )

                val destinationAddress = JsonMapper.convertPatientAddressToJSON(
                    PatientAddress(
                        destinationName,
                        longitudeDestination,
                        latitudeDestination,
                    )
                )

                return remoteDataSource.updatePatient(
                    id, token, name, age, symptom, notes, watchCode, homeAddress, destinationAddress
                )
            }
        }.asFlow()
    }

    override fun getPatient(id: String, token: String): Flow<Resource<Patient>> {
        return object : NetworkBoundResource<Patient, PatientResponse>() {
            override suspend fun fetchFromApi(response: PatientResponse): Patient {
                return PatientDataMapper.mapPatientResponseToDomain(response)
            }

            override suspend fun createCall(): Flow<ApiResponse<PatientResponse>> {
                return remoteDataSource.getPatient(id, token)
            }
        }.asFlow()
    }

    override fun updatePatientLocations(
        token: String,
        id: String,
        addressName: String,
        longitudeHome: Double,
        latitudeHome: Double,
        destinationName: String,
        longitudeDestination: Double,
        latitudeDestination: Double,
    ): Flow<Resource<Patient>> {
        return object : NetworkBoundResource<Patient, PatientResponse>() {
            override suspend fun fetchFromApi(response: PatientResponse): Patient {
                return PatientDataMapper.mapPatientResponseToDomain(response)
            }

            override suspend fun createCall(): Flow<ApiResponse<PatientResponse>> {
                val homeAddress = JsonMapper.convertPatientAddressToJSON(
                    PatientAddress(
                        addressName,
                        longitudeHome,
                        latitudeHome,
                    )
                )

                val destinationAddress = JsonMapper.convertPatientAddressToJSON(
                    PatientAddress(
                        destinationName,
                        longitudeDestination,
                        latitudeDestination,
                    )
                )

                return remoteDataSource.updatePatientLocations(
                    id,
                    token,
                    homeAddress,
                    destinationAddress
                )
            }
        }.asFlow()
    }

    override fun getHistoryPatient(token: String, watchId: String): Flow<Resource<PatientHistory>> {
        return object : NetworkBoundResource<PatientHistory, PatientHistoryResponse>() {
            override suspend fun fetchFromApi(response: PatientHistoryResponse): PatientHistory {
                return PatientDataMapper.mapHistoryPatientResponseToDomain(response)
            }

            override suspend fun createCall(): Flow<ApiResponse<PatientHistoryResponse>> {
                return remoteDataSource.getHistoryPatient(token, watchId)
            }
        }.asFlow()
    }
}