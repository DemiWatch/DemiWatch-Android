package com.project.demiwatch.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryItem(
    val condition: String,
    val duration: String,
    val start: String,
    val end: String,

    val homeAddress: String,
    val destinationAddress: String,
) : Parcelable