package com.project.demiwatch.core.data.source.remote.datasource

import com.google.gson.Gson
import com.project.demiwatch.core.data.source.remote.network.ApiResponse
import com.project.demiwatch.core.data.source.remote.network.ApiService
import com.project.demiwatch.core.data.source.remote.response.auth.LoginResponse
import com.project.demiwatch.core.data.source.remote.response.auth.RegisterResponse
import com.project.demiwatch.core.data.source.remote.response.patient.PatientHistoryResponse
import com.project.demiwatch.core.data.source.remote.response.patient.PatientLocationResponse
import com.project.demiwatch.core.data.source.remote.response.patient.PatientResponse
import com.project.demiwatch.core.data.source.remote.response.user.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    //USER

    suspend fun loginUser(email: String, password: String): Flow<ApiResponse<LoginResponse>> {
        return flow {
            try {
                val response = apiService.loginUser(email, password)

                if (response.status == 200) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                Timber.tag("loginUser").d(e.toString())
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun registerUser(email: String, password: String): Flow<ApiResponse<RegisterResponse>> {
        return flow {
            try {
                val response = apiService.registerUser(email, password)

                if (response.status == 200) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                Timber.tag("loginUser").d(e.toString())
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun addUser(
        token: String,
        id: String,
        name: String,
        phoneNumber: String,
        status: String,
        maxRadius: String,
    ): Flow<ApiResponse<UserResponse>> {
        return flow {
            try {
                val response = apiService.addUser(token, id, name, phoneNumber, status, maxRadius)

                if (response.data != null) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                Timber.tag("addUser").d(e.toString())
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun updateUser(
        token: String,
        id: String,
        name: String,
        phoneNumber: String,
        status: String,
        maxRadius: String,
    ): Flow<ApiResponse<UserResponse>> {
        return flow {
            try {
                val response =
                    apiService.updateUser(token, id, name, phoneNumber, status, maxRadius)

                if (response.data != null) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                Timber.tag("updateUser").d(e.toString())
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUser(
        id: String, token: String
    ): Flow<ApiResponse<UserResponse>> {
        return flow {
            try {
                val response = apiService.getUser(token, id)

                if (response.data != null) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                Timber.tag("getUser").d(e.toString())
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    //END-USER


    //PATIENT

    suspend fun getLocationPatient(
        token: String,
        watchId: String
    ): Flow<ApiResponse<PatientLocationResponse>> {
        return flow<ApiResponse<PatientLocationResponse>> {
            try {
                val response = apiService.getLocationPatient(token, watchId)

                if (response.status == 200) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                Timber.tag("loginUser").d(e.toString())
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun addPatient(
        token: String,
        name: String,
        age: Int,
        symptom: String,
        note: String,
        watchCode: String,
        homeAddress: String,
        destinationAddress: String,
    ): Flow<ApiResponse<PatientResponse>> {
        return flow<ApiResponse<PatientResponse>> {
            try {
                val response = apiService.addPatient(
                    token,
                    name,
                    age,
                    symptom,
                    note,
                    watchCode,
                    homeAddress,
                    destinationAddress
                )

                if (response.data != null) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                Timber.tag("addPatient").d(e.toString())
                emit(ApiResponse.Error(e.toString()))
            }

        }.flowOn(Dispatchers.IO)
    }

    suspend fun updatePatient(
        id: String,
        token: String,
        name: String,
        age: Int,
        symptom: String,
        note: String,
        watchCode: String,
        homeAddress: String,
        destinationAddress: String,
    ): Flow<ApiResponse<PatientResponse>> {
        return flow<ApiResponse<PatientResponse>> {
            try {
                val response = apiService.updatePatient(
                    id,
                    token,
                    name,
                    age,
                    symptom,
                    note,
                    watchCode,
                    homeAddress,
                    destinationAddress
                )

                if (response.data != null) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                Timber.tag("updatePatient").d(e.toString())
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPatient(id: String, token: String): Flow<ApiResponse<PatientResponse>> {
        return flow<ApiResponse<PatientResponse>> {
            try {
                val response = apiService.getPatient(token, id)
                Timber.tag("getPatient").d("JSON Response: ${Gson().toJson(response)}")

                if (response.data != null) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                Timber.tag("getPatient2").d(e.toString())
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun updatePatientLocations(
        id: String, token: String, homeAddress: String,
        destinationAddress: String,
    ): Flow<ApiResponse<PatientResponse>> {
        return flow<ApiResponse<PatientResponse>> {
            try {
                val response = apiService.updatePatientLocations(
                    id,
                    token,
                    homeAddress,
                    destinationAddress
                )

                if (response.data != null) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                Timber.tag("updatePatientLocations").d(e.toString())
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getHistoryPatient(
        token: String,
        watchId: String
    ): Flow<ApiResponse<PatientHistoryResponse>> {
        return flow<ApiResponse<PatientHistoryResponse>> {
            try {
                val response = apiService.getHistoryPatient(token, watchId)

                if (response.status == 200) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                Timber.tag("getHistoryPatient").d(e.toString())
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    //END-PATIENT
}