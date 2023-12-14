package com.project.demiwatch.core.data.source.remote.response.patient

import com.google.gson.annotations.SerializedName

data class PatientLocationResponse(

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("location")
    val location: Location? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null,

    @field:SerializedName("emergency")
    val emergency: String? = null,
)

data class Location(

    @field:SerializedName("latitude")
    val latitude: Any? = null,

    @field:SerializedName("kode")
    val kode: String? = null,

    @field:SerializedName("longitude")
    val longitude: Any? = null
)
