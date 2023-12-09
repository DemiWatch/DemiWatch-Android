package com.project.demiwatch.core.utils.data_mapper

import com.project.demiwatch.core.data.source.remote.response.auth.LoginResponse
import com.project.demiwatch.core.data.source.remote.response.auth.RegisterResponse
import com.project.demiwatch.core.data.source.remote.response.user.UserResponse
import com.project.demiwatch.core.domain.model.Auth
import com.project.demiwatch.core.domain.model.User

object UserDataMapper {
    fun mapLoginResponseToDomain(input: LoginResponse?): Auth = Auth(
        status = input?.status ?: 401,
        token = input?.token ?: "token",
        error = input?.error ?: "error",
        userId = input?.id ?: "error",
        detail = input?.detail ?: "Terjadi kesalahan, silahkan lakukan login ulang",
    )

    fun mapRegisterResponseToDomain(input: RegisterResponse?): Auth = Auth(
        status = input?.status ?: 401,
        error = input?.error ?: "error",
        message = input?.message ?: "message",
        userId = input?.id ?: "error",
    )

    fun mapUserResponseToDomain(input: UserResponse): User = User(
        name = input.data?.nama ?: "Nama User",
        id = input.data?.id ?: "Id User",
        email = input.data?.email ?: "Email User",
        teleNumber = input.data?.telepon ?: "No Telpon User",
        status = input.data?.status ?: "Status User",
        safeRadius = input.data?.radius ?: "Radius Aman User",
        error = input.error ?: "Error Response",
        patientId = input.data?.patientId?.first() ?: "Id Pasien"
    )
}