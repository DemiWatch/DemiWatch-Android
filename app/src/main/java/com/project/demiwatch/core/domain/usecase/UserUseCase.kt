package com.project.demiwatch.core.domain.usecase

import com.project.demiwatch.core.domain.model.Auth
import com.project.demiwatch.core.domain.model.User
import com.project.demiwatch.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun loginUser(email: String, password: String): Flow<Resource<Auth>>

    fun registerUser(email: String, password: String): Flow<Resource<Auth>>

    fun getTokenUser(): Flow<String>

    suspend fun saveTokenUser(token: String)

    fun getIdUser(): Flow<String>

    suspend fun saveIdUser(userId: String)

    suspend fun deleteTokenUser()

    //without profile picture
    fun addUser(
        token: String,
        id: String,
        name: String,
        radius: String,
        status: String,
        phoneNumber: String
    ): Flow<Resource<User>>

    fun updateUser(
        token: String,
        id: String,
        name: String,
        radius: String,
        status: String,
        phoneNumber: String
    ): Flow<Resource<User>>

    fun getUser(token: String, id: String): Flow<Resource<User>>
}