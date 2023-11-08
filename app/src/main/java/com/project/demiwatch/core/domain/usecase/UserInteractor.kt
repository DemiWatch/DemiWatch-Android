package com.project.demiwatch.core.domain.usecase

import androidx.lifecycle.asLiveData
import com.project.demiwatch.core.data.repository.UserRepository
import com.project.demiwatch.core.domain.model.Auth
import com.project.demiwatch.core.domain.model.User
import com.project.demiwatch.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInteractor @Inject constructor(private val userRepository: UserRepository): UserUseCase {
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

    override suspend fun deleteTokenUser() {
        return userRepository.deleteTokenUser()
    }

//    override fun registerUser(name: String, email: String, password: String): Flow<Resource<User>> {
//        return userRepository.registerUser(name, email, password)
//    }

}