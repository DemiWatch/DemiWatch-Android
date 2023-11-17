package com.project.demiwatch.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val id: String,
    val email: String,
    val teleNumber: String,
    val status: String,
    val safeRadius: String,
    val error: String,
) : Parcelable
