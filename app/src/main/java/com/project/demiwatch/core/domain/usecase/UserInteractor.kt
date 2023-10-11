package com.project.demiwatch.core.domain.usecase

import com.project.demiwatch.core.data.repository.UserRepository
import com.project.demiwatch.core.domain.model.User
import com.project.demiwatch.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInteractor @Inject constructor(private val userRepository: UserRepository): UserUseCase {
    override fun loginUser(email: String, password: String): Flow<Resource<User>> {
        return userRepository.loginUser(email, password)
    }

//    override fun registerUser(name: String, email: String, password: String): Flow<Resource<User>> {
//        return userRepository.registerUser(name, email, password)
//    }

}