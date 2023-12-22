package com.project.demiwatch.core.data.source.remote.response.patient

import com.google.gson.annotations.SerializedName

data class PatientResponse(

    @field:SerializedName("data")
    val data: PatientData? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("error")
    val error: String? = null,
)

data class AlamatTujuan(
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("longi")
    val longi: Any? = null,

    @field:SerializedName("lat")
    val lat: Any? = null
)

data class AlamatRumah(
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("longi")
    val longi: Any? = null,

    @field:SerializedName("lat")
    val lat: Any? = null
)

data class PatientData(

    @field:SerializedName("umur")
    val umur: Int? = null,

    @field:SerializedName("alamatTujuan")
    val alamatTujuan: AlamatTujuan? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("kode")
    val kode: String? = null,

    @field:SerializedName("__v")
    val v: Int? = null,

    @field:SerializedName("catatan")
    val catatan: String? = null,

    @field:SerializedName("jenisPenyakit")
    val jenisPenyakit: String? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("alamatRumah")
    val alamatRumah: AlamatRumah? = null
)
