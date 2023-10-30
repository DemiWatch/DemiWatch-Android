package com.project.demiwatch.core.data.repository

import com.project.demiwatch.core.data.source.local.LocalDataSource
import com.project.demiwatch.core.data.source.remote.NetworkBoundResource
import com.project.demiwatch.core.data.source.remote.datasource.RemoteDataSource
import com.project.demiwatch.core.data.source.remote.network.ApiResponse
import com.project.demiwatch.core.data.source.remote.response.LoginResponse
import com.project.demiwatch.core.domain.model.Auth
import com.project.demiwatch.core.domain.model.User
import com.project.demiwatch.core.domain.repository.IUserRepository
import com.project.demiwatch.core.utils.Resource
import com.project.demiwatch.core.utils.data_mapper.UserDataMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
private val remoteDataSource: RemoteDataSource,
private val localDataSource: LocalDataSource
) : IUserRepository{
    override fun loginUser(email: String, password: String): Flow<Resource<Auth>> {
        return object:NetworkBoundResource<Auth, LoginResponse>(){
            override suspend fun fetchFromApi(response: LoginResponse): Auth {
                return UserDataMapper.mapLoginResponseToDomain(response)
            }

            override suspend fun createCall(): Flow<ApiResponse<LoginResponse>> {
                return remoteDataSource.loginUser(email, password)
            }

        }.asFlow()
    }

    override fun getTokenUser(): Flow<String> {
        return localDataSource.getUserToken()
    }

    override suspend fun saveTokenUser(token: String) {
        return localDataSource.saveUserToken(token)
    }

    override suspend fun deleteTokenUser() {
       return localDataSource.deleteTokenUser()
    }

//    override fun registerUser(name: String, email: String, password: String): Flow<Resource<User>> {
//        return object: NetworkBoundResource<User, RegisterResponse>(){
//            override suspend fun fetchFromApi(response: RegisterResponse): User {
//                return UserDataMapper
//            }
//
//            override suspend fun createCall(): Flow<ApiResponse<RegisterResponse>> {
//                TODO("Not yet implemented")
//            }
//
//        }
//    }

}