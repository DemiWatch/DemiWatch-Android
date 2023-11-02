package com.project.demiwatch.core.domain.repository

import com.project.demiwatch.core.domain.model.Auth
import com.project.demiwatch.core.domain.model.User
import com.project.demiwatch.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun loginUser(email:String, password: String): Flow<Resource<Auth>>

    fun getTokenUser(): Flow<String>

    suspend fun saveTokenUser(token: String)

    suspend fun deleteTokenUser()

}