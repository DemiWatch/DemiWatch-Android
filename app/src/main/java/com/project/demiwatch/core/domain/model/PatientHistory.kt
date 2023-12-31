package com.project.demiwatch.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PatientHistory(
    // patient data
    val id: String,
    val name: String,
    val symptom: String,

    // history data
    val historyList: List<HistoryItem>
) : Parcelable