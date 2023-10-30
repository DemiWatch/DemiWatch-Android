package com.project.demiwatch.core.domain.usecase

import com.project.demiwatch.core.domain.model.Auth
import com.project.demiwatch.core.domain.model.User
import com.project.demiwatch.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun loginUser(email:String, password: String): Flow<Resource<Auth>>

    fun getTokenUser(): Flow<String>

    suspend fun saveTokenUser(token: String)

    suspend fun deleteTokenUser()

//    fun registerUser(name: String, email: String, password: String):Flow<Resource<User>>
}