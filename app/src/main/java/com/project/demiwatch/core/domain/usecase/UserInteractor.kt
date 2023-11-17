package com.project.demiwatch.core.domain.usecase

import com.project.demiwatch.core.data.repository.UserRepository
import com.project.demiwatch.core.domain.model.Auth
import com.project.demiwatch.core.domain.model.User
import com.project.demiwatch.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInteractor @Inject constructor(private val userRepository: UserRepository) : UserUseCase {
    override fun loginUser(email: String, password: String): Flow<Resource<Auth>> {
        return userRepository.loginUser(email, password)
    }

    override fun registerUser(email: String, password: String): Flow<Resource<Auth>> {
        return userRepository.registerUser(email, password)
    }

    override fun getTokenUser(): Flow<String> {
        return userRepository.getTokenUser()
    }

    override suspend fun saveTokenUser(token: String) {
        return userRepository.saveTokenUser(token)
    }

    override fun getIdUser(): Flow<String> {
        return userRepository.getIdUser()
    }

    override suspend fun saveIdUser(userId: String) {
        return userRepository.saveIdUser(userId)
    }

    override suspend fun deleteTokenUser() {
        return userRepository.deleteTokenUser()
    }

    override fun addUser(
        token: String,
        id: String,
        name: String,
        radius: String,
        status: String,
        phoneNumber: String
    ): Flow<Resource<User>> {
        return userRepository.addUser(id, token, name, radius, status, phoneNumber)
    }

    override fun updateUser(
        token: String,
        id: String,
        name: String,
        radius: String,
        status: String,
        phoneNumber: String
    ): Flow<Resource<User>> {
        return userRepository.updateUser(id, token, name, radius, status, phoneNumber)
    }

    override fun getUser(token: String, id: String): Flow<Resource<User>> {
        return userRepository.getUser(id, token)
    }

}