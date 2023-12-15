package com.project.demiwatch.core.data.source.remote.response.patient

import com.google.gson.annotations.SerializedName

data class PatientHistoryResponse(

    @field:SerializedName("alamatTujuan")
    val alamatTujuan: Address? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("jenisPenyakit")
    val jenisPenyakit: String? = null,

    @field:SerializedName("durations")
    val durations: List<DurationsItem?>? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("tanggal")
    val tanggal: String? = null,

    @field:SerializedName("alamatRumah")
    val alamatRumah: Address? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class Address(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("longi")
    val longi: Any? = null,

    @field:SerializedName("lat")
    val lat: Any? = null
)

data class DurationsItem(

    @field:SerializedName("duration")
    val duration: String? = null,

    @field:SerializedName("condition")
    val condition: String? = null,

    @field:SerializedName("start")
    val start: String? = null,

    @field:SerializedName("end")
    val end: String? = null,

    @field:SerializedName("alamatRumah")
    val homeAddress: Address? = null,

    @field:SerializedName("alamatTujuan")
    val destinationAddress: Address? = null
)
