package com.project.demiwatch.core.domain.repository

import com.project.demiwatch.core.domain.model.Auth
import com.project.demiwatch.core.domain.model.User
import com.project.demiwatch.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun loginUser(email: String, password: String): Flow<Resource<Auth>>

    fun registerUser(email: String, password: String): Flow<Resource<Auth>>

    fun getTokenUser(): Flow<String>

    suspend fun saveTokenUser(token: String)

    fun getIdUser(): Flow<String>

    suspend fun saveIdUser(userId: String)

    suspend fun deleteTokenUser()

    //without profile picture
    fun addUser(
        id: String,
        token: String,
        name: String,
        radius: String,
        status: String,
        phoneNumber: String
    ): Flow<Resource<User>>

    fun updateUser(
        id: String,
        token: String,
        name: String,
        radius: String,
        status: String,
        phoneNumber: String
    ): Flow<Resource<User>>

    fun getUser(id: String, token: String): Flow<Resource<User>>
}