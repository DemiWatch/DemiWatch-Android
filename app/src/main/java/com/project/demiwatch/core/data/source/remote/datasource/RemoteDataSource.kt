package com.project.demiwatch.core.data.source.remote.datasource

import android.util.Log
import com.project.demiwatch.core.data.source.remote.network.ApiResponse
import com.project.demiwatch.core.data.source.remote.network.ApiService
import com.project.demiwatch.core.data.source.remote.response.LoginResponse
import com.project.demiwatch.core.data.source.remote.response.RegisterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
                if(response != null){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                Timber.tag("loginUser").d( e.toString())
                emit(ApiResponse.Error(e.toString()))
            }
        }
    }

//    suspend fun registerUser(name:String, email: String, password: String): Flow<ApiResponse<RegisterResponse>> {
//        return flow{
//            try {
//                val response = apiService.registerUser(email, email, password)
//                if(response != null){
//                    emit(ApiResponse.Success(response))
//                }else{
//                    emit(ApiResponse.Empty)
//                }
//            }catch (e: Exception){
//                Timber.tag("loginUser").d( e.toString())
//                emit(ApiResponse.Error(e.toString()))
//            }
//        }
//    }

    //END-USER

    //PATIENT



    //END-PATIENT
}