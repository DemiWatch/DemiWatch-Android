package com.project.demiwatch.core.data.repository

import com.project.demiwatch.core.data.source.local.LocalDataSource
import com.project.demiwatch.core.data.source.remote.NetworkBoundResource
import com.project.demiwatch.core.data.source.remote.datasource.RemoteDataSource
import com.project.demiwatch.core.data.source.remote.network.ApiResponse
import com.project.demiwatch.core.data.source.remote.response.patient.PatientLocationResponse
import com.project.demiwatch.core.domain.model.PatientLocation
import com.project.demiwatch.core.domain.repository.IPatientRepository
import com.project.demiwatch.core.utils.Resource
import com.project.demiwatch.core.utils.data_mapper.PatientDataMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PatientRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
): IPatientRepository{
    override fun getLocationPatient(token: String): Flow<Resource<PatientLocation>> {
        return object:NetworkBoundResource<PatientLocation, PatientLocationResponse>(){
            override suspend fun fetchFromApi(response: PatientLocationResponse): PatientLocation {
                return PatientDataMapper.mapPatientLocationResponseToDomain(response)
            }

            override suspend fun createCall(): Flow<ApiResponse<PatientLocationResponse>> {
                return remoteDataSource.getLocationPatient(token)
            }

        }.asFlow()
    }


}