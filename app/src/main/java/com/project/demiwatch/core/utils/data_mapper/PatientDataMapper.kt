package com.project.demiwatch.core.utils.data_mapper

import com.project.demiwatch.core.data.source.remote.response.patient.PatientLocationResponse
import com.project.demiwatch.core.domain.model.PatientLocation

object PatientDataMapper {
    fun mapPatientLocationResponseToDomain(input: PatientLocationResponse?): PatientLocation = PatientLocation(
        status = input?.status ?: 401,
        message = input?.message,
        latitude = input?.location?.latitude as Double,
        longitude = input.location.longitude as Double,
        kode = input.location.kode
    )

}