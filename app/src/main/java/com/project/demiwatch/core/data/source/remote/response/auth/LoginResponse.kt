package com.project.demiwatch.core.data.source.remote.response.auth

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("error")
	val error: String? = null,

	@field:SerializedName("detail")
	val detail: String? = null,

	@field:SerializedName("id")
	val id: String? = null,
)
