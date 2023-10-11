package com.project.demiwatch.core.utils.datamapper

import com.project.demiwatch.core.data.source.remote.response.LoginResponse
import com.project.demiwatch.core.data.source.remote.response.RegisterResponse
import com.project.demiwatch.core.domain.model.User

object UserDataMapper {
    fun mapLoginResponseToDomain(input: LoginResponse?):User = User(
        id =  "id",
        email = "email",
        password = "password",
        name = input?.name ?: "name",
        token = input?.token ?: "token",
        userStatus = "user status",
        safeRadius = "safe radius",
        teleNumber = "tele number"
    )

//    fun mapRegisterResponseToDomain(input: RegisterResponse?): User = User(
//        id =  "id",
//        email = "email",
//        password = "password",
//        name ="name",
//        token ="token",
//        userStatus = "user status",
//        safeRadius = "safe radius",
//        teleNumber = "tele number"
//    )
}