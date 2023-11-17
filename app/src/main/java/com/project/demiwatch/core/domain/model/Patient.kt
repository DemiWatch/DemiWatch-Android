package com.project.demiwatch.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Patient(
    val id: String,
    val name: String,
    val age: Int,
    val symptom: String,
    val watchCode: String,
    val homeName: String,
    val longitudeHome: Double,
    val latitudeHome: Double,
    val destinationName: String,
    val longitudeDestination: Double,
    val latitudeDestination: Double,
    val note: String,
    val error: String,
) : Parcelable
