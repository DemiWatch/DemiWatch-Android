package com.project.demiwatch.core.utils.data_mapper

import com.google.gson.Gson
import com.project.demiwatch.core.domain.model.PatientAddress

object JsonMapper {
    fun convertPatientAddressToJSON(address: PatientAddress): String = Gson().toJson(address)
}