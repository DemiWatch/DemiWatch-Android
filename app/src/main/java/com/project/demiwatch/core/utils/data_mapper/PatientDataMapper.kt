package com.project.demiwatch.core.utils.data_mapper

import com.project.demiwatch.core.data.source.remote.response.patient.*
import com.project.demiwatch.core.domain.model.*

object PatientDataMapper {
    fun mapPatientLocationResponseToDomain(input: PatientLocationResponse?): PatientLocation =
        PatientLocation(
            status = input?.status ?: 401,
            message = input?.message,
            latitude = input?.location?.latitude as Double,
            longitude = input.location.longitude as Double,
            kode = input.location.kode,
            emergency = input.emergency != "false",
        )

    fun mapPatientResponseToDomain(input: PatientResponse?): Patient = Patient(
        id = input?.data?.id!!,
        name = input.data.nama ?: "Nama Pasien",
        age = input.data.umur ?: 99,
        symptom = input.data.jenisPenyakit ?: "Jenis Penyakit",
        watchCode = input.data.kode ?: "P002",
        homeName = input.data.alamatRumah?.name ?: "Nama Alamat Rumah",
        latitudeHome = input.data.alamatRumah?.lat as Double,
        longitudeHome = input.data.alamatRumah.longi as Double,
        destinationName = input.data.alamatTujuan?.name ?: "Nama Alamat Tujuan",
        latitudeDestination = input.data.alamatTujuan?.lat as Double,
        longitudeDestination = input.data.alamatTujuan.longi as Double,
        note = input.data.catatan ?: "Catatan Pasien",
        error = input.error ?: "Error Response"
    )

    fun mapHistoryItemToDomain(input: List<DurationsItem?>?): List<HistoryItem> = input!!.map {
        HistoryItem(
            condition = it?.condition ?: "kendala",
            duration = it?.duration ?: "0 hours 0 minutes",
            start = it?.start ?: "2023-11-25 03:04:46",
            end = it?.end ?: "2023-11-25 03:04:46",
            homeAddress = "${it?.homeAddress?.name} (${it?.homeAddress?.lat} ; ${it?.homeAddress?.longi})",
            destinationAddress = "${it?.destinationAddress?.name} (${it?.destinationAddress?.lat} ; ${it?.destinationAddress?.longi})"
        )
    }

    private fun mapAddressToDomain(input: Address): PatientAddress = PatientAddress(
        name = input.name ?: "Nama Lokasi",
        longitude = input.longi as Double,
        latitude = input.lat as Double,
    )

    fun mapHistoryPatientResponseToDomain(input: PatientHistoryResponse): PatientHistory =
        PatientHistory(
            id = input.id!!,
            name = input.nama ?: "Nama Pasien",
            symptom = input.jenisPenyakit ?: "Jenis Penyakit",
            historyList = mapHistoryItemToDomain(input.durations)
        )
}