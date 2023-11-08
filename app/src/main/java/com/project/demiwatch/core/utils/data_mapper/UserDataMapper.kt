package com.project.demiwatch.core.utils.data_mapper

import com.project.demiwatch.core.data.source.remote.response.auth.LoginResponse
import com.project.demiwatch.core.data.source.remote.response.auth.RegisterResponse
import com.project.demiwatch.core.domain.model.Auth

object UserDataMapper {
    fun mapLoginResponseToDomain(input: LoginResponse?): Auth = Auth(
        status =  input?.status ?: 401,
        name = input?.name ?: "name",
        token = input?.token ?: "token",
        error = input?.error ?: "error",
        detail = input?.detail ?: "Terjadi kesalahan, silahkan lakukan login ulang",
    )

    fun mapRegisterResponseToDomain(input: RegisterResponse?): Auth = Auth(
        status =  input?.status ?: 401,
        error = input?.error ?: "error",
        message = input?.message ?: "message",
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