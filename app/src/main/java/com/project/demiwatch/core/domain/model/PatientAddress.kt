package com.project.demiwatch.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PatientAddress(
    val name: String,
    val longitude: Double,
    val latitude: Double,
) : Parcelable
