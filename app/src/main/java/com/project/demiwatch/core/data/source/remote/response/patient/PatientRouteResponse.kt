package com.project.demiwatch.core.data.source.remote.response.patient

import com.google.gson.annotations.SerializedName

data class PatientRouteResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class DataItem(

	@field:SerializedName("instruction")
	val instruction: String? = null,

	@field:SerializedName("bearing_after")
	val bearingAfter: Int? = null,

	@field:SerializedName("bearing_before")
	val bearingBefore: Int? = null,

	@field:SerializedName("location")
	val location: List<Any?>? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("modifier")
	val modifier: String? = null
)
