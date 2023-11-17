package com.project.demiwatch.core.data.source.remote.response.auth

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
	
    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null,

    @field:SerializedName("error")
    val error: String? = null,

    @field:SerializedName("id")
    val id: String? = null,
)
