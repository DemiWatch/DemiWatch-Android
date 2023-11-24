package com.project.demiwatch.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PatientProfileCache(
    val name: String,
    val age: String,
    val patientSymptoms: String,
    val notes: String,
    val watchCode: String,
    val homeAddress: String,
    val destinationAddress: String,
) : Parcelable