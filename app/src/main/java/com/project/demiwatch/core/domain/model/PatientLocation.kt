package com.project.demiwatch.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PatientLocation(
    val status: Int,
    val message: String? = null,
    val longitude: Double? = null,
    val latitude: Double? = null,
    val kode: String? = null,
    val emergency: Boolean? = null,
) : Parcelable