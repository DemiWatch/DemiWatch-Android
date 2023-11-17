package com.project.demiwatch.core.data.source.remote.response.user

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("data")
    val data: UserData? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("error")
    val error: String? = null,
)

data class UserData(

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("__v")
    val v: Int? = null,

    @field:SerializedName("telepon")
    val telepon: String? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("radius")
    val radius: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)
