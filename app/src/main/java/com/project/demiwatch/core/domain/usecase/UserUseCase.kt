package com.project.demiwatch.core.domain.usecase

import com.project.demiwatch.core.domain.model.User
import com.project.demiwatch.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun loginUser(email:String, password: String): Flow<Resource<User>>

//    fun registerUser(name: String, email: String, password: String):Flow<Resource<User>>
}