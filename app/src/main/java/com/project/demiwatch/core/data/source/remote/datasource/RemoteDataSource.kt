package com.project.demiwatch.core.data.source.remote.datasource

import com.project.demiwatch.core.data.source.remote.network.ApiResponse
import com.project.demiwatch.core.data.source.remote.network.ApiService
import com.project.demiwatch.core.data.source.remote.response.auth.LoginResponse
import com.project.demiwatch.core.data.source.remote.response.auth.RegisterResponse
import com.project.demiwatch.core.data.source.remote.response.patient.PatientLocationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService){

    //USER

    suspend fun loginUser(email: String, password: String): Flow<ApiResponse<LoginResponse>> {
        return flow{
            try {
                val response = apiService.loginUser(email, password)
                if(response.status == 200){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                Timber.tag("loginUser").d( e.toString())
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun registerUser(email: String, password: String): Flow<ApiResponse<RegisterResponse>> {
        return flow{
            try {
                val response = apiService.registerUser(email, password)
                if(response.status == 200){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                Timber.tag("loginUser").d( e.toString())
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    //END-USER


    //PATIENT

    suspend fun getLocationPatient(token: String): Flow<ApiResponse<PatientLocationResponse>>{
        return flow<ApiResponse<PatientLocationResponse>> {
            try {
                val response = apiService.getLocationPatient(token)
                if (response.status == 200){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                Timber.tag("loginUser").d( e.toString())
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    //END-PATIENT
}