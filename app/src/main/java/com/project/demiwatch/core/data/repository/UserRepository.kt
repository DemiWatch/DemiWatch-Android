package com.project.demiwatch.core.data.repository

import com.project.demiwatch.core.data.source.local.LocalDataSource
import com.project.demiwatch.core.data.source.remote.NetworkBoundResource
import com.project.demiwatch.core.data.source.remote.datasource.RemoteDataSource
import com.project.demiwatch.core.data.source.remote.network.ApiResponse
import com.project.demiwatch.core.data.source.remote.response.auth.LoginResponse
import com.project.demiwatch.core.data.source.remote.response.auth.RegisterResponse
import com.project.demiwatch.core.data.source.remote.response.user.UserResponse
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
) : IUserRepository {
    override fun loginUser(email: String, password: String): Flow<Resource<Auth>> {
        return object : NetworkBoundResource<Auth, LoginResponse>() {
            override suspend fun fetchFromApi(response: LoginResponse): Auth {
                return UserDataMapper.mapLoginResponseToDomain(response)
            }

            override suspend fun createCall(): Flow<ApiResponse<LoginResponse>> {
                return remoteDataSource.loginUser(email, password)
            }
        }.asFlow()
    }

    override fun registerUser(email: String, password: String): Flow<Resource<Auth>> {
        return object : NetworkBoundResource<Auth, RegisterResponse>() {
            override suspend fun fetchFromApi(response: RegisterResponse): Auth {
                return UserDataMapper.mapRegisterResponseToDomain(response)
            }

            override suspend fun createCall(): Flow<ApiResponse<RegisterResponse>> {
                return remoteDataSource.registerUser(email, password)
            }
        }.asFlow()
    }

    override fun getTokenUser(): Flow<String> {
        return localDataSource.getUserToken()
    }

    override suspend fun saveTokenUser(token: String) {
        return localDataSource.saveUserToken(token)
    }

    override fun getIdUser(): Flow<String> {
        return localDataSource.getIdUser()
    }

    override suspend fun saveIdUser(userId: String) {
        return localDataSource.saveIdUser(userId)
    }

    override suspend fun deleteTokenUser() {
        return localDataSource.deleteTokenUser()
    }

    override fun addUser(
        id: String,
        token: String,
        name: String,
        radius: String,
        status: String,
        phoneNumber: String
    ): Flow<Resource<User>> {
        return object : NetworkBoundResource<User, UserResponse>() {
            override suspend fun fetchFromApi(response: UserResponse): User {
                return UserDataMapper.mapUserResponseToDomain(response)
            }

            override suspend fun createCall(): Flow<ApiResponse<UserResponse>> {
                return remoteDataSource.addUser(token, id, name, phoneNumber, status, radius)
            }

        }.asFlow()
    }

    override fun updateUser(
        id: String,
        token: String,
        name: String,
        radius: String,
        status: String,
        phoneNumber: String
    ): Flow<Resource<User>> {
        return object : NetworkBoundResource<User, UserResponse>() {
            override suspend fun fetchFromApi(response: UserResponse): User {
                return UserDataMapper.mapUserResponseToDomain(response)
            }

            override suspend fun createCall(): Flow<ApiResponse<UserResponse>> {
                return remoteDataSource.updateUser(token, id, name, phoneNumber, status, radius)
            }

        }.asFlow()
    }

    override fun getUser(id: String, token: String): Flow<Resource<User>> {
        return object : NetworkBoundResource<User, UserResponse>() {
            override suspend fun fetchFromApi(response: UserResponse): User {
                return UserDataMapper.mapUserResponseToDomain(response)
            }

            override suspend fun createCall(): Flow<ApiResponse<UserResponse>> {
                return remoteDataSource.getUser(id, token)
            }

        }.asFlow()
    }


}