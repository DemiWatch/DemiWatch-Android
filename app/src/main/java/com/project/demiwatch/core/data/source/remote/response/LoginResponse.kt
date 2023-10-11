package com.project.demiwatch.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("token")
	val token: String? = null
)
