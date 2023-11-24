package com.project.demiwatch.core.utils.data_mapper

import com.google.gson.Gson
import com.project.demiwatch.core.domain.model.PatientAddress
import com.project.demiwatch.core.domain.model.PatientProfileCache

object JsonMapper {
    fun convertPatientAddressToJSON(address: PatientAddress): String = Gson().toJson(address)

    fun convertPatientProfileToJson(patientProfile: PatientProfileCache): String =
        Gson().toJson(patientProfile)

    fun convertToPatientProfile(jsonValue: String): PatientProfileCache =
        Gson().fromJson(jsonValue, PatientProfileCache::class.java)
}