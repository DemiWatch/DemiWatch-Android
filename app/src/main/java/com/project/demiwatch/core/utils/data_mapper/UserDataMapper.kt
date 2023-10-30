package com.project.demiwatch.core.utils.data_mapper

import com.project.demiwatch.core.data.source.remote.response.LoginResponse
import com.project.demiwatch.core.domain.model.Auth
import com.project.demiwatch.core.domain.model.User

object UserDataMapper {
    fun mapLoginResponseToDomain(input: LoginResponse?): Auth = Auth(
        status =  input?.status ?: 401,
        name = input?.name ?: "",
        token = input?.token ?: "",
        error = input?.error ?: "",
        detail = input?.detail ?: "Terjadi kesalahan, silahkan lakukan login ulang",
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